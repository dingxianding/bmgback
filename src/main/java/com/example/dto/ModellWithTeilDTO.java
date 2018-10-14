package com.example.dto;

import com.example.entity.Aggregate;
import com.example.entity.Modell;
import com.example.entity.Platform;
import com.example.entity.Teil;

import java.util.List;


public class ModellWithTeilDTO extends PageResultDTO {
    private Modell modell;
    private List<Teil> teils;

    public Modell getModell() {
        return modell;
    }

    public void setModell(Modell modell) {
        this.modell = modell;
    }

    public List<Teil> getTeils() {
        return teils;
    }

    public void setTeils(List<Teil> teils) {
        this.teils = teils;
    }
}
