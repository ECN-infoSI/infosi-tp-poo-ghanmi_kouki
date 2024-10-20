/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.woe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe Lapin sous classe de Monstre définit les caractéristiques d'un Monstre Lapin
 * @author nourkouki
 * @author dghanmi
 */

public class Lapin extends Monstre {
    
    // constructeurs
    /**
     * Un constructeur de la classe Lapin sous-classe de Monstre avec 6 parametres
     * @param pV: points de vie
     * @param dA: degats d'attaque
     * @param pPar: points de parade
     * @param paAtt: Pourcentage d'attaque
     * @param paPar: pourcentage de parade
     * @param p: position du monstre 
     * 
    */
    public Lapin(int pV, int dA, int pPar, int paAtt, int paPar, Point2D p, String nom){
        super(pV, dA, pPar, paAtt, paPar, p, nom);
    
    }
    /**
     * Constructeur de recopie de la classe Lapin
     * permet de creer un monstre à partir d'un autre lapin existant
     * prend un seul parametre
     * @param l: un lapin exitant
     */
    public Lapin(Lapin l){
        super(l);
        this.pos= new Point2D(l.pos);
    }
    /**
     * Constructeur par défaut
     */
    
    public Lapin(){
    
    }
    
    /**
     *
     * methode de sauvegarde dans la BDN
     * @param connection: connection à la BDN
     * @param ID_sauvegarde: identifiant de la sauvegarde
     * @param i: incrément i
     */

    public void saveToDatabase(Connection connection, int ID_sauvegarde, int i) {
    try {
        // Première requête pour insérer dans la table 'creature'
        String queryCreature = "INSERT INTO creature(id_creature, pos_x, pos_y) VALUES (?, ?, ?)";
        PreparedStatement stmCreature = connection.prepareStatement(queryCreature);
        stmCreature.setString(1, "c-" + i);          // id_creature (ex: 'c-1')
        stmCreature.setInt(2, this.pos.getX());      // pos_x
        stmCreature.setInt(3, this.pos.getY());      // pos_y
        stmCreature.executeUpdate();
        // Deuxième requête pour insérer dans la table 'monstre'
        String queryMonstre = "INSERT INTO monstre(id_mon, id_creature, type_monstre, ptvie, ptdeg, ptpar, ptatt, papar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmMonstre = connection.prepareStatement(queryMonstre);
        stmMonstre.setString(1, "lapin" + i);         // id_mon (ex: 'lapin1')
        stmMonstre.setString(2, "c-" + i);           // id_creature (ex: 'c-1')
        stmMonstre.setString(3, "Lapin");             // type_monstre
        stmMonstre.setInt(4, this.ptVie);                 // pt_vie
        stmMonstre.setInt(5, this.degAtt);                // deg_att
        stmMonstre.setInt(6, this.ptPar);                 // pt_par
        stmMonstre.setInt(7, this.pageAtt);               // page_att
        stmMonstre.setInt(8, this.pagePar);               // page_att
        stmMonstre.executeUpdate();

        // Confirmation dans la console
        System.out.println("Lapin nom: lapin" + i);
    } catch (SQLException ex) {
        Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    /**
     * methode de chargement de la BDN
     * @param connection: connection à la BDN
     * @param id: identifiant de la sauvegarde
     * @param nom_monstre: nom du monstre
     */

    public void getFromDatabase(Connection connection, Integer id, String nom_monstre) {
    try {
        // Requête pour récupérer les informations du monstre et de la créature
        String query = "SELECT m.id_mon, c.pos_x, c.pos_y "
                     + "FROM monstre m "
                     + "INNER JOIN creature c USING(id_creature) "
                     + "INNER JOIN est_dans_une_sauv s USING(id_creature) "
                     + "INNER JOIN partie p on s.id_partie=p.id_partie "
                     + "WHERE s.id_sauvegarde = ? AND m.type_mon = 'Lapin' AND m.id_mon = ?";
        
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setString(1, id.toString());                // Paramètre 1 : id_sauvegarde
        stm.setString(2, nom_monstre);    // Paramètre 2 : id_mon
        
        ResultSet rs = stm.executeQuery();

        // Si des résultats sont trouvés, récupérer les coordonnées
        if (rs.next()) {
            this.pos.setX(rs.getInt("pos_x"));
            this.pos.setY(rs.getInt("pos_y"));
            System.out.println("nom_monstre: " + nom_monstre + " x: " + this.pos.getX() + " y: " + this.pos.getY());
        } else {
            System.out.println("Aucun Lapin trouvé avec le nom: " + nom_monstre);
        }
    } catch (SQLException ex) {
        Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}
