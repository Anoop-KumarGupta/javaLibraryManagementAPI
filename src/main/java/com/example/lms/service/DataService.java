package com.example.lms.service;

import com.example.lms.model.*;
import com.example.lms.repositories.*;
import com.opencsv.CSVWriter;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Service
public class DataService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private IssuedRepository issuedRepository;
    @Autowired
    private MemberRepository memberRepository;

    private String CSVFILEPATH="src/main/resources/csv/export";


    public String exportAllDataToCsv() throws Exception {
        try {
            exportAdminDataToCSV();
            exportAuthorDataToCSV();
            exportBookDataToCSV();
            exportIssuedDataToCSV();
            exportMemberDataToCSV();
        }catch(Exception e){
            throw new Exception("Could not perform the operation due to the following reasons: "+e.getMessage(),e);
        }
        return "Data has been exported successfully";
    }


    @PostConstruct
    public void exportAdminDataToCSV() throws Exception {
        List<Admin> admins = adminRepository.findAll();
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSVFILEPATH+"/adminOut.csv"))) {
            writer.writeNext(new String[]{"Id", "Name", "Position"});
            for (Admin admin : admins) {
                writer.writeNext(new String[]{
                        admin.getAdminId()+"",
                        admin.getAdminName(),
                        admin.getAdminPosition()
                });
            }
        } catch (IOException e) {
            throw new Exception("could not perform the operation due to following reason: "+e.getMessage(),e);
        }
    }

    public void exportAuthorDataToCSV() throws Exception {
        List<Author> authors=authorRepository.findAll();
        try(CSVWriter writer=new CSVWriter(new FileWriter(CSVFILEPATH+"/authorOut.csv"))){
            writer.writeNext(new String[]{"Id","Name","Bio"});
            for(Author author: authors){
                writer.writeNext(new String[]{
                        author.getAuthorId()+"",
                        author.getAuthorName(),
                        author.getAuthorBio()
                });
            }
        }catch(Exception e){
            throw new Exception("could not perform the operation due to following reason: "+e.getMessage(),e);
        }
    }

    public void exportBookDataToCSV() throws Exception {
        List<Book> books=bookRepository.findAll();
        try(CSVWriter writer=new CSVWriter(new FileWriter(CSVFILEPATH+"/bookOut.csv"))){
            writer.writeNext(new String[]{"Id","Name","Author","Category","Publisher"});
            for(Book book:books){
                writer.writeNext(new String[]{
                        book.getBookId()+"",
                        book.getBookName(),
                        book.getAuthor()==null?"":book.getAuthor().getAuthorName(),
                        book.getCategoryName(),
                        book.getPublisherName(),
                });
            }
        }catch(Exception e){
            throw new Exception("could not perform the operation due to following reason: "+e.getMessage(),e);
        }
    }

    public void exportIssuedDataToCSV() throws Exception {
        List<Issued> issuedList=issuedRepository.findAll();
        try(CSVWriter writer=new CSVWriter(new FileWriter(CSVFILEPATH+"/issuedOut.csv"))){
            writer.writeNext(new String[]{"Id","IssuedDate","IssuedTill","BookName","MemberName"});
            for(Issued issued:issuedList){
                writer.writeNext(new String[]{
                        issued.getIssuedId()+"",
                        issued.getIssuedDate(),
                        issued.getIssuedTill(),
                        issued.getBook().getBookName(),
                        issued.getMember().getMemberName()
                });
            }
        }catch(Exception e){
            throw new Exception("could not perform the operation due to following reason: "+e.getMessage(),e);
        }
    }

    public void exportMemberDataToCSV() throws Exception {
        List<Member> members=memberRepository.findAll();
        try(CSVWriter writer=new CSVWriter(new FileWriter(CSVFILEPATH+"/memberOut.csv"))){
            writer.writeNext(new String[]{"Id","Name","Age","BookIssued"});
            for(Member member:members){
                writer.writeNext(new String[]{
                        member.getMemberId()+"",
                        member.getMemberName(),
                        member.getMemberAge(),
                        "yes"
                });
            }
        }catch(Exception e){
            throw new Exception("could not perform the operation due to following reason: "+e.getMessage(),e);
        }
    }
}
