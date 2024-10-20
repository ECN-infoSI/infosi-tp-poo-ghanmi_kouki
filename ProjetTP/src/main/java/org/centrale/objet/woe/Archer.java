/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.woe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * la classe Archer sous classe de Personnage définit le personnage Archer
 * @author nourkouki
 * @author dghanmi
 */


public class Archer extends Personnage implements Combattant {
    //Attributs
    /**
     * le nombre de fleches de l'Archer
     */
    public int nbFleches;

    // Constructeurs
    /**
     * 
     * @param n: du personnage
     * @param pV: les points de vie du personnage
     * @param dA: degats d'attaque
     * @param pPar: points de parade
     * @param paAtt: Pourcentage d'attaque
     * @param paPar: pourcentage de parade
     * @param dMax: distance maximale d'attaque
     * @param p: position du personnage 
     * @param nbFleches : nombre de fleches
     */
    public Archer(String n, int pV, int dA, int pPar, int paAtt, int paPar, int dMax, Point2D p, int nbFleches) {
        super(pV, dA, pPar, paAtt, paPar, p, n, dMax);
        this.nbFleches = nbFleches;
    }

    public Archer(Archer a) {
        super(a);
        this.nbFleches = a.nbFleches;
    }

    public Archer() {
        super();
    }
    
    
    /** 
     * Methode combattre définit le systeme de combat 
     * @param c : une creature
     */
    public void combattre(Creature c){
        double d = this.pos.distance(c.pos);
        boolean attaque = true;
        Random x = new Random();
        if (d == 1) {
            int y = x.nextInt(100);
            System.out.println("Rand du tirage aléatoire" + y);
            if (y > this.pageAtt) {
                attaque = false;
            }
            System.out.println("attaque " + attaque);
            if (attaque) {
                int z = x.nextInt(100);
                if (z > c.pagePar) {
                    c.ptVie = this.degAtt;
                } else {
                    c.ptVie -= (this.ptVie - c.ptPar);
                }
            }
        }
        if ((d < this.distAttMax) && (d > 1)) {
            int y = x.nextInt(100);
            if (y > this.pageAtt) {
                attaque = false;
            }
            if (attaque) {
                this.nbFleches-=1;
                c.ptVie -= this.degAtt;

            }
        }
        if (d >= this.distAttMax) {
            System.out.println("Echec");
        }

    }

    /**
     *
     * @param connection
     * @param ID_sauvegarde
     * @param i
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
            String query = "INSERT INTO humanoide(nom_hum, id_hum, id_creature, type_hum, ptvie, ptdeg, ptpar, ptatt, dist_max_att, nbr_fleches) "
             + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);

            // Utilisation des paramètres pour les valeurs
            stm.setString(1, this.nom);                // nom_hum
            stm.setString(2, "h-" + i);                // id_hum (ex: 'h-1')
            stm.setString(3, "c-" + i);                // id_creature (ex: 'c-1')
            stm.setString(4, "Archer");                // type_hum (toujours 'Archer')
            stm.setInt(5, this.ptVie);                 // pt_vie
            stm.setInt(6, this.degAtt);                // deg_att
            stm.setInt(7, this.ptPar);                 // pt_par
            stm.setInt(8, this.pageAtt);               // page_att
            stm.setInt(9, this.distAttMax);           // dist_att_max
            stm.setInt(10, this.nbFleches);            // nb_fleches
            // Exécuter la requête
            stm.executeUpdate();
            System.out.println("Archer nom:" + this.nom + i);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     *
     * @param connection
     * @param id
     * @param nom_humanoide
     */

    public void getFromDatabase(Connection connection, Integer id, String nom_humanoide) {
        try {
            String query = "SELECT h.nom_hum, h.id_hum, c.pos_x, c.pos_y FROM humanoide h "
                         + "INNER JOIN creature c on h.id_creature=c.id_creature "
                         + "INNER JOIN est_dans_une_sauv s on s.id_creature=c.id_creature "
                         + "INNER JOIN partie p on s.id_partie=p.id_partie "
                         + "WHERE p.id_sauvegarde = ? AND h.type_hum = 'Archer' AND h.nom_hum = ?";
            
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
                System.out.println("Archer chargé : nom=" + this.nom + " position=(" + this.pos.getX() + ", " + this.pos.getY() + ")");
            } while (rs.next());
}
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
}

