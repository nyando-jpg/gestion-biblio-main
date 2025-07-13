package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Pret;
import com.springjpa.repository.PretRepository;

@Service
public class PretService {
    @Autowired
    private PretRepository pretRepository;

    public Pret findByAdherantAndExemplaire(Integer idAdherant, Integer idExemplaire) {
        return pretRepository.findTopByAdherantIdAdherantAndExemplaireIdExemplaireOrderByDateDebutDesc(idAdherant, idExemplaire);
    }

    public Pret findById(Integer id){
        return pretRepository.findById(id).get();
    }

    public List<Pret> findAll(){
        return pretRepository.findAll();
    }

    public void save(Pret pret){
        pretRepository.save(pret);
    }
    
    public long countActivePretsByAdherantAndType(Integer idAdherant, Integer idTypePret) {
        return pretRepository.countActivePretsByAdherantAndType(idAdherant, idTypePret);
    }
}
