package com.alissonvisa.batchfilereader.batch;

import org.springframework.batch.item.file.mapping.DefaultLineMapper;

import java.nio.charset.StandardCharsets;

public class RawLineMapper extends DefaultLineMapper<String> {

    private final String importArchive;

    public RawLineMapper(String importArchive) {
        this.importArchive = importArchive;
    }

    @Override
    public String mapLine(String line, int lineNumber) {
        return getUtf8Line(line) + concatArchive();
    }

    private String getUtf8Line(String line) {
        return new String(line.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    private String concatArchive() {
        return "\u00E7" + importArchive.toUpperCase();
    }
}
