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
 * Classe Paysan sous classe de Personnage définit les caractéristiques d'un Paysan
 * @author nourkouki
 * @author dghanmi
 */
public class Paysan extends Personnage {

    // Constructeurs
    /**
     * 
     * @param n: nom du personnage
     * @param pV: les points de vie du personnage
     * @param dA: degats d'attaque
     * @param pPar: points de parade
     * @param paAtt: Pourcentage d'attaque
     * @param paPar: pourcentage de parade
     * @param dMax: distance maximale d'attaque
     * @param p: position du personnage 
     *
     */
    public Paysan(String n, int pV, int dA, int pPar, int paAtt, int paPar, int dMax, Point2D p) {
        super(pV, dA, pPar, paAtt, paPar, p, n, dMax);
    }
    /**
     * Constructeur de recopie
     * @param p : un paysan déja existant
     */

    public Paysan(Paysan p) {
        super(p);
        this.pos= new Point2D(p.pos);
    }
    
    /**
     * Constructeur par defaut
     */
    public Paysan() {
        super();
    }
    
    /**
    * methode de sauvegarde dans la BDN
     * @param connection: connection à la BDN
     * @param ID_sauvegarde: identifiant de la sauvegarde
     * @param i: incrément i
     */
 
    public void saveToDatabase(Connection connection, int ID_sauvegarde, int i) {
        try {
            String Query1 = "insert into creature(id_creature, pos_x, pos_y) VALUES (?, ?, ?)";
            PreparedStatement stm1 = connection.prepareStatement(Query1);
            // Utilisation de paramètres pour les valeurs
            stm1.setString(1, "c-" + i);                 // id_creature
            stm1.setInt(2, this.pos.getX());                  // pos_x
            stm1.setInt(3, this.pos.getY());                  // pos_y
            stm1.executeUpdate();
            String query = "INSERT INTO humanoide(nom_hum, id_hum, id_creature, type_hum, ptvie, ptdeg, ptpar, ptatt, dist_max_att) "
             + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);

            // Utilisation des paramètres pour les valeurs
            stm.setString(1, this.nom);                // nom_hum
            stm.setString(2, "h-" + i);                // id_hum (ex: 'h-1')
            stm.setString(3, "c-" + i);                // id_creature (ex: 'c-1')
            stm.setString(4, "Paysan");                // type_hum 
            stm.setInt(5, this.ptVie);                 // pt_vie
            stm.setInt(6, this.degAtt);                // deg_att
            stm.setInt(7, this.ptPar);                 // pt_par
            stm.setInt(8, this.pageAtt);               // page_att
            stm.setInt(9, this.distAttMax);           // dist_att_max
            // Exécuter la requête
            stm.executeUpdate();
            System.out.println("Paysan nom:" + this.nom + i);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     * methode de chargement de la BDN
     * @param connection: connection à la BDN
     * @param id: identifiant de la sauvegarde
     * @param nom_humanoide: nom du personnage
     */
    
    public void getFromDatabase(Connection connection, Integer id, String nom_humanoide) {
        try {
            String query = "SELECT h.nom_hum, h.id_hum, c.pos_x, c.pos_y FROM humanoide h "
                         + "INNER JOIN creature c on h.id_creature=c.id_creature "
                         + "INNER JOIN est_dans_une_sauv s on s.id_creature=c.id_creature "
                         + "INNER JOIN partie p on s.id_partie=p.id_partie "
                         + "WHERE p.id_sauvegarde = ? AND h.type_hum = 'Paysan' AND h.nom_hum = ?";
            
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, id.toString());
            stm.setString(2, nom_humanoide);
            ResultSet rs = stm.executeQuery();
            if (!rs.next()) {
                System.out.println("Aucun résultat trouvé pour id_sauvegarde=" + id + " et nom_humanoide=" + nom_humanoide);
            } else {
            do {
                this.nom = rs.getString("nom_hum");
                this.pos.setX(rs.getInt("pos_x"));
                this.pos.setY(rs.getInt("pos_y"));
                System.out.println("Paysan chargé : nom=" + this.nom + " position=(" + this.pos.getX() + ", " + this.pos.getY() + ")");
            } while (rs.next());
}
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
