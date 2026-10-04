
package model;

import java.math.BigDecimal;

public class Job {
    private long id;
    private long categoryId;
    private String title;
    private BigDecimal salary;
    private String status;

    public Job(long id, long categoryId, String title,
               BigDecimal salary, String status) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.salary = salary;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
