/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.woe;

import java.util.Random;

/**
 * la classe Personnage permet d'attribuer des caractéristiques à un personnage
 * @author nourkouki
 * @author dghanmi
 */

public abstract class Personnage extends Creature{
    
    //Attributs de la classe
    
    /**
     * distance d'attaque maximale
     */
    public int distAttMax;
    
    // Constructeurs
    /**
     * Un constructeur de la classe Personnage avec 8 parametres
     * @param pV: les points de vie du personnage
     * @param dA: degats d'attaque
     * @param pPar: points de parade
     * @param paAtt: Pourcentage d'attaque
     * @param paPar: pourcentage de parade
     * @param dMax: distance maximale d'attaque
     * @param p: position du personnage 
     * @param nom: nom du personnage
    */
    
    public Personnage(int pV, int dA, int pPar, int paAtt, int paPar, Point2D p, String nom, int dMax) {
        super(pV, dA, pPar, paAtt, paPar, p ,nom);
        this.distAttMax = dMax;
    }

    /**
     * Un constructeur de recopie de la classe Personnage
     * permet de creer un personnage à partir d'un autre personnage existant
     * @param perso: un personnage exitant
     */
    
    public Personnage(Personnage perso) {
        super(perso);
        this.nom=perso.nom;
        this.distAttMax=perso.distAttMax;
    }
    
    /**
     * Un constructeur par défaut de la classe Personnage
     * permet de creer un personnage avec des valeurs par defaut
     */
    
    public Personnage() {
        super();
    }

    // Accesseurs et modificateurs
    
    /**
     * retourne le nom du personnage
     * @return nom
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * retourne les points de vie
     * @return ptVie
     */

    public int getPtVie() {
        return ptVie;
    }
    /**
     * retourne le degré d'attaque
     * @return degAtt
     */

    public int getDegAtt() {
        return degAtt;
    }
    /**
     * retourne les Points de parade
     * @return ptPar
     */

    public int getPtPar() {
        return ptPar;
    }
    
    /**
     * retourne le Pourcentage d'attaque
     * @return pageAtt
     */
    
    public int getPageAtt() {
        return pageAtt;
    }
    /**
     * retourne le Pourcentage de Parade
     * @return pagePar
     */

    public int getPagePar() {
        return pagePar;
    }
    /**
     * retourne la distance d'attaque maximale.
     * @return distAttMax
     */

    public int getDistAttMax() {
        return distAttMax;
    }
    /**
     * 
     * @return position
     */

    public Point2D getPos() {
        return pos;
    }
    
    /**
     * modifie le nom du personnage
     * @param n : le nom du personnage
     */

    public void setNom(String n) {
        this.nom = n;
    }
    
    /**
     * modifie les points de vie
     * 
     * @param ptVie: les points de vie
     */

    public void setPtVie(int ptVie) {
        this.ptVie = ptVie;
    }
    /**
     * modifie le degré d'attaque
     * 
     * @param degAtt: le degré d'attaque
     */

    public void setDegAtt(int degAtt) {
        this.degAtt = degAtt;
    }
    
    /**
     * modifie les points de parade
     * 
     * @param ptPar: les points de parade
     */
    public void setPtPar(int ptPar) {
        this.ptPar = ptPar;
    }
    /**
     * modifie le pourcentage d'attaque
     * 
     * @param pageAtt: le pourcentage d'attaque
     */
    public void setPageAtt(int pageAtt) {
        this.pageAtt = pageAtt;
    }
    
    /**
     * modifie le pourcentage de parade
     * 
     * @param pagePar: le pourcentage de parade
     */
    public void setPagePar(int pagePar) {
        this.pagePar = pagePar;
    }
    
    /**
     * modifie la distance d'attaque maximale
     * 
     * @param distAttMax: la distance d'attaque maximale
     */

    public void setDistAttMax(int distAttMax) {
        this.distAttMax = distAttMax;
    }
    
    /**
     * modifie la position
     * 
     * @param pos: la position
     */
    public void setPos(Point2D pos) {
        this.pos = pos;
    }
    
    // Méthode de déplacement aléatoire
    /**
     * methode deplace permet de deplacer aléatoirement un objet sur une case adjacente
     * de la ou il se trouve
     */

    public void deplace() {
        Random rand = new Random();
        // Déplacement aléatoire entre -1 et 1 sur les axes x et y
        int deltaX = rand.nextInt(3) - 1; // Valeur aléatoire entre -1 et 1
        int deltaY = rand.nextInt(3) - 1;

        // Mettre à jour la position
        this.pos.setX(this.pos.getX() + deltaX);
        this.pos.setY(this.pos.getY() + deltaY);
    }

    // Methode affiche
    /**
     * methode affiche permet d'afficher les caractéristique d'un personnage
     */
    public void affiche() {
    System.out.println("Nom : " + nom);
    System.out.println("Points de vie : " + ptVie);
    System.out.println("Dégâts d'attaque : " + degAtt);
    System.out.println("Points de parade : " + ptPar);
    System.out.println("Pourcentage d'attaque : " + pageAtt);
    System.out.println("Pourcentage de parade : " + pagePar);
    System.out.println("Distance d'attaque maximale : " + distAttMax);
    System.out.print("Position : ");
    if (this.pos !=null){
        this.pos.afficher();
    }
    else{
        System.out.println('F');
    }
    
    }
    
    
}



