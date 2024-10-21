package org.centrale.objet.woe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sous classe d'objet gérant les épées
 * @author nourkouki
 * @author dghanmi
 */
public class Epee extends Objet implements Utilisable{
    /**
     * Attribut de la class
     * degAtt: pts de Vie que la potion donne
     */
    private int degAtt;
    private int dureeEffet;
   /**
    * Constructeur à partir de deux paramètres et la classe Objet
    * @param pos: position de l'objet
    * @param pts: Points expérience gagnés grâce à l'objet - Peut être ignoré pour le moment
    * @param degAtt: degats d'attaque
    */
    public Epee(Point2D pos,int degAtt,int dureeEffet) {
        super(pos, 10,"");
        this.degAtt = degAtt;
        this.dureeEffet = dureeEffet;
    }
    public void use(Joueur j){
        Personnage p = j.getPersonnage();
        p.setDegAtt(p.getDegAtt()+this.degAtt);
    }
    public int BuffDuration(){
        return this.dureeEffet;
    }
    public void SetBuffDuration(int b){
        this.dureeEffet=b;
    }
    public void DebuffAfterEnd(Joueur j){
        Personnage p = j.getPersonnage();
        p.setDegAtt(p.getDegAtt()-this.degAtt);
    }
    public int getBuffDetails(){
        return this.degAtt;
    }
    
    /**
     *
     * @param connection
     * @param ID_sauvegarde
     * @param i
     */
    public void saveToDatabase(Connection connection, int ID_sauvegarde, int i) {
    try {
        // Requête pour insérer un objet dans la table "Objet"
        String query = "INSERT INTO Objet(nom_objet, XP, x, y) VALUES (?, ?, ?, ?)";
        PreparedStatement stm = connection.prepareStatement(query);
        
        // Définir les paramètres pour la requête
        stm.setString(1, "epee" + i);          // nom_objet (ex: 'epee1')
        stm.setInt(2, this.XP);                // XP (expérience de l'objet)
        stm.setInt(3, this.pos.getX());        // x (coordonnée X)
        stm.setInt(4, this.pos.getY());        // y (coordonnée Y)

        // Exécuter la requête
        stm.executeUpdate();

        // Confirmation dans la console
        System.out.println("Epee nom: epee" + i + " à la position: [" + this.pos.getX() + "," + this.pos.getY() + "]");
        } catch (SQLException ex) {
        Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    /**
     *
     * @param connection
     * @param id
     * @param nom_objet
     */
    public void getFromDatabase(Connection connection, Integer id, String nom_objet) {
    try {
        // Requête avec des paramètres pour éviter la concaténation
        String query = "SELECT o.nom_objet, o.XP, o.x, o.y "
                     + "FROM objet o "
                     + "INNER JOIN est_dans_une_sauv s on s.nom_objet=o.nom_objet "
                     + "INNER JOIN partie p on s.id_partie=p.id_partie "
                     + "WHERE p.id_sauvegarde = ? AND o.nom_objet = ?";
        
        PreparedStatement stm = connection.prepareStatement(query);
        
        // Définir les paramètres pour la requête
        stm.setString(1, id.toString());                // Paramètre 1 : id_sauvegarde
        stm.setString(2, nom_objet);       // Paramètre 3 : nom_epee
        
        // Exécuter la requête
        ResultSet rs = stm.executeQuery();

        // Si un résultat est trouvé, récupérer les valeurs
        if (rs.next()) {
            if (this.pos == null) {
                this.pos = new Point2D();  // Initialiser pos si elle est null
            }
            this.pos.setX(rs.getInt("x"));  // Récupérer x
            this.pos.setY(rs.getInt("y"));  // Récupérer y
            this.XP = rs.getInt("XP");      // Récupérer les points d'expérience (XP)
            
            System.out.println("Nom de l'épée : " + nom_objet + " Position: [" + this.pos.getX() + ", " + this.pos.getY() + "] XP: " + this.XP);
        } else {
            System.out.println("Aucune épée trouvée avec le nom: " + nom_objet);
        }
    } catch (SQLException ex) {
        Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
    }
}

}
