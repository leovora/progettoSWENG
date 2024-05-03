//--------TUTTO COMMENTATO PERCHé CON L'AUTOINCREMENT GENERA DEI NUOVI ELEMENTI NEL DB---------
//-------DA RIVEDERE

//package com.progettosweng.application.generator;
//
//import com.progettosweng.application.entity.Storia;
//import com.progettosweng.application.service.StoriaService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class StoriaGenerator  implements CommandLineRunner {
//
//    @Autowired
//    private StoriaService storiaService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        generateStorie();
//    }
//
//    private void generateStorie() {
//        // Genera e salva le storie nel database
//        Storia storia1 = new Storia(1,"I 3 porcellini", "In questa storia si impersona un lupo affamato", 10);
//        Storia storia2 = new Storia(2,"I 5 porcellini", "In questa storia si impersona un porcellino inculato", 5);
//
//
//        storiaService.saveStoria(storia1);
//        storiaService.saveStoria(storia2);
//
//        System.out.println("Storie generate e salvate nel database.");
//    }
//
//}
