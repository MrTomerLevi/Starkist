package com.bytbale.starkist.model;

import javax.persistence.*;

/**
 * Created by tomerlev on 16/01/2017.
 */
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    String name;
    String cron;
    String file;


    @Enumerated(EnumType.STRING)
    Source source;

    public enum Source{
        HDFS, S3, LOCAL_FS
    }

    public Job(String name,String cron, String file, Source source) {
        this.name = name;
        this.cron = cron;
        this.file = file;
        this.source = source;
    }

    public Job() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (id != null ? !id.equals(job.id) : job.id != null) return false;
        if (name != null ? !name.equals(job.name) : job.name != null) return false;
        if (cron != null ? !cron.equals(job.cron) : job.cron != null) return false;
        if (file != null ? !file.equals(job.file) : job.file != null) return false;
        return source == job.source;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cron != null ? cron.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Job{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", cron='").append(cron).append('\'');
        sb.append(", file='").append(file).append('\'');
        sb.append(", source=").append(source);
        sb.append('}');
        return sb.toString();
    }
}
