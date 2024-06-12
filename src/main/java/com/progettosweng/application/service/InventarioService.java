package com.progettosweng.application.service;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioService {

    @Autowired
    InventarioRepository inventarioRepository;

    /**
     * Salva un oggetto nell'inventario nel database.
     * @param inventario l'oggetto inventario da salvare
     */
    public void saveOggettoInventario(Inventario inventario) {
        inventarioRepository.save(inventario);
    }

    /**
     * Elimina l'inventario associato a una storia dal database.
     * @param storia la storia di cui eliminare l'inventario
     */
    public void deleteInventarioByStoria(Storia storia) {
        inventarioRepository.deleteByStoria(storia);
    }

    /**
     * Elimina l'inventario di un utente associato a una storia dal database.
     * @param user l'utente di cui eliminare l'inventario
     * @param storia la storia di cui eliminare l'inventario
     */
    public void deleteInventarioUser(AbstractUser user, Storia storia) {
        inventarioRepository.deleteByUser(user, storia);
    }

    /**
     * Verifica se un oggetto è presente nell'inventario di un utente.
     * @param user l'utente di cui controllare l'inventario
     * @param oggetto l'oggetto da controllare
     * @return true se l'oggetto è presente nell'inventario dell'utente, altrimenti false
     */
    public Boolean checkOggettoInventario(AbstractUser user, Oggetto oggetto) {
        return inventarioRepository.checkOggetto(user, oggetto);
    }
}
