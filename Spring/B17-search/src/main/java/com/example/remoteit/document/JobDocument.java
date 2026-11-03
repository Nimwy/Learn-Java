package com.example.remoteit.document;

import com.example.remoteit.model.Job;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "jobs")
public class JobDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String company;

    @Field(type = FieldType.Text)
    private String location;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String searchText;

    protected JobDocument() {
    }

    public static JobDocument from(Job job) {
        JobDocument document = new JobDocument();
        document.id = job.getId();
        document.title = job.getTitle();
        document.company = job.getCompany();
        document.location = job.getLocation();
        document.description = job.getDescription();
        document.searchText = String.join(" ",
                job.getTitle(), job.getCompany(), job.getLocation(), job.getDescription());
        return document;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getCompany() { return company; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getSearchText() { return searchText; }
}
