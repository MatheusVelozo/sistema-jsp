package beanDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BeanDtoGraficoPessoas implements Serializable {
    public static final long serialVersionUID = 1L;

    List<String> perfils = new ArrayList<String>();
    List<Integer> totPessoas = new ArrayList<Integer>();

    public List<String> getPerfils() {
        return perfils;
    }

    public void setPerfils(List<String> perfils) {
        this.perfils = perfils;
    }

    public List<Integer> getTotPessoas() {
        return totPessoas;
    }

    public void setTotPessoas(List<Integer> totPessoas) {
        this.totPessoas = totPessoas;
    }
}
