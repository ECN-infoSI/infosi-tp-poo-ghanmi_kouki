package org.centrale.objet.woe;

/**
 * Classe Objet gérant les objets
 * @author nourkouki
 * @author dghanmi
 */
public class Objet {
    // Attributs de la classe
     /** 
      * position de l'objet
      */
   
    public Point2D pos;
    /**
     * Points expérience gagnés grâce à l'objet
     */
    public int XP;
    // constructeur
    /**
     * Constructeur de la classe Objet
     * @param pos: position de l'objet
     * @param pts: Points expérience gagnés grâce à l'objet 
     */
    public Objet (Point2D pos,int pts) {
        this.pos=pos;
        this.XP=pts;
    }
}
