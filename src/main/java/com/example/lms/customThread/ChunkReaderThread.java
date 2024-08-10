package com.example.lms.customThread;

import com.example.lms.model.Author;
import com.example.lms.repositories.AuthorRepository;
import com.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class ChunkReaderThread implements Callable<Void>{

    private static final Logger logger = LogManager.getLogger(ChunkReaderThread.class);


    private final String filePath;
    private final int startLine;
    private final int endLine;
    private final AuthorRepository authorRepository;

    public ChunkReaderThread(String filePath, int startLine, int endLine, AuthorRepository authorRepository) {
        this.filePath = filePath;
        this.startLine = startLine;
        this.endLine = endLine;
        this.authorRepository = authorRepository;
    }

    @Override
    public Void call() throws Exception {
        logger.info("StartLine: "+startLine+" EndLine: "+endLine+" ThreadId: "+Thread.currentThread());
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            for (int i = startLine; i < endLine; i++) {
                String[] record=csvReader.readNext();
                Author author = new Author();
                author.setAuthorName(record[0]);
                author.setAuthorBio(record[1]);
                authorRepository.save(author);
            }
        } catch (Exception e) {
            throw new Exception("Could not perform the operation due to :"+e.getMessage(),e);
        }
        return null;
    }
}






