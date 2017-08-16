package org.inn.baner.bean;

/**
 * 名称：  Subject
 * 作者:   rain.wang
 * 日期:   2017/8/16
 * 简介:
 */
public class Subject {
    private String subjectid;
    private int weight;

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void addWeight(int val){
        this.weight = this.weight + val;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Subject)){
            return false;
        }else{
            //主体类型及id一致，则认为该主体一致
            if(this.subjectid.equals(((Subject) obj).getSubjectid())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Subject{");
        sb.append("subjectid='").append(subjectid).append('\'');
        sb.append(", weight=").append(weight);
        sb.append('}');
        return sb.toString();
    }
}
