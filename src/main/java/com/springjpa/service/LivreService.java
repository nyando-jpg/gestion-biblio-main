package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Livre;
import com.springjpa.repository.LivreRepository;
import com.springjpa.repository.ExemplaireRepository;

@Service
public class LivreService {
    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    public Livre findById(Integer id){
        return livreRepository.findById(id).get();
    }

    public List<Livre> findAll(){
        List<Livre> livres = livreRepository.findAll();
        
        // Ajouter le nombre d'exemplaires pour chaque livre
        for (Livre livre : livres) {
            long nombreExemplaires = exemplaireRepository.countByLivreIdLivreAndDispo(livre.getIdLivre(), true) + 
                                   exemplaireRepository.countByLivreIdLivreAndDispo(livre.getIdLivre(), false);
            livre.setNombreExemplaires((int) nombreExemplaires);
        }
        
        return livres;
    }

    public void save(Livre livre){
        livreRepository.save(livre);
    }
}
