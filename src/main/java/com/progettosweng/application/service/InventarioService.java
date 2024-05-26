package com.progettosweng.application.service;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioService {

    @Autowired
    InventarioRepository inventarioRepository;

    public Inventario saveOggettoInventario(Inventario inventario) { return inventarioRepository.save(inventario);}

    public void deleteInventarioStoria(Storia storia) { inventarioRepository.deleteByStoria(storia);}

    public Boolean checkOggettoInventario(AbstractUser user, Oggetto oggetto) { return inventarioRepository.checkOggetto(user, oggetto);}
}
