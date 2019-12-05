package fr.istic.prg1.tree;

import java.awt.Dimension;
import java.util.Scanner;

import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2016-04-20
 * 
 *        Classe décrivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 * 
 */

public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * @param x
	 *            abscisse du point
	 * @param y
	 *            ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {
		Dimension dim = new Dimension(256,256);
		int xbas = 0;
		int ybas = 0;
		int cpt = 0;
		Iterator<Node> it = this.iterator();
		while(it.getValue().state == 2) {
			cpt++;
			if(cpt%2 == 1) {
				if((dim.height + ybas)/2 <= y){
					ybas = (dim.height + ybas)/2;
					it.goRight();
				}else {
					dim.height = (dim.height + ybas)/2;
					it.goLeft();
				}
			}else {
				if((dim.width + xbas)/2 <= x){
					xbas = (dim.width+ xbas)/2;
					it.goRight();
				}else {
					dim.width = (dim.width+ xbas)/2;
					it.goLeft();
				}
			}
		}
		if(it.getValue().state == 1) {
			return true;
		}else {
			return false;
		}

	}

	/**
	 * this devient identique à image2.
	 *
	 * @param image2
	 *            image à copier
	 *
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void affect(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		affectAux(it,it2);
	}
	
	public void affectAux(Iterator<Node> it,Iterator<Node> it2) {
		if(it2.nodeType().equals(NodeType.LEAF)) {
			it.addValue(it2.getValue());
		}
		if(it2.nodeType().equals(NodeType.DOUBLE)) {

			it.addValue(it2.getValue());

			it2.goLeft();
			it.goLeft();

			affectAux(it,it2);

			it2.goUp();
			it.goUp();

			it2.goRight();
			it.goRight();
			
			
			affectAux(it,it2);
			
			it2.goUp();
			it.goUp();
		}
	}

	/**
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		rotateAux(it,it2);
	}
	
	public void rotateAux(Iterator<Node> it,Iterator<Node> it2) {
		if(it2.nodeType().equals(NodeType.LEAF)) {
			it.addValue(it2.getValue());

		}

		if(it2.nodeType().equals(NodeType.DOUBLE)) {

			it.addValue(it2.getValue());

			it2.goLeft();
			it.goRight();

			rotateAux(it,it2);

			it2.goUp();
			it.goUp();

			it2.goRight();
			it.goLeft();
			
			
			rotateAux(it,it2);
			
			it2.goUp();
			it.goUp();
		}
	}

	/**
	 * this devient rotation de image2 à 90 degrés dans le sens des aiguilles
	 * d'une montre.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate90(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction non demeand�e");
		System.out.println("-------------------------------------------------");
		System.out.println();	    
	}

	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		Iterator<Node> it = this.iterator();
		inverseAux(it);
	}
	public void inverseAux(Iterator<Node> it) {
		if(it.nodeType().equals(NodeType.LEAF)) {
			it.setValue(Node.valueOf(1-it.getValue().state));

		}

		if(it.nodeType().equals(NodeType.DOUBLE)) {
	
			it.goRight();

			inverseAux(it);
	
			it.goUp();
	
			it.goLeft();
						
			inverseAux(it);
				
			it.goUp();
		}
	}

	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		int cpt = 1;
		it.clear();
		mirrorVAux(it,it2, cpt);
	}

	public void mirrorVAux(Iterator<Node> it, Iterator<Node> it2, int cpt) {
		if (cpt%2 == 1) { // on est en d�coupage vertical
			if(it2.nodeType().equals(NodeType.LEAF)) {
				it.addValue(it2.getValue());
			}

			if(it2.nodeType().equals(NodeType.DOUBLE)) {

				it.addValue(it2.getValue());

				it2.goLeft();
				it.goRight();

				mirrorVAux(it,it2, 2);

				it2.goUp();
				it.goUp();

				it2.goRight();
				it.goLeft();
				
				
				mirrorVAux(it,it2,2);
				
				it2.goUp();
				it.goUp();
			}
			
		}
		else {
			if(it2.nodeType().equals(NodeType.LEAF)) {
				it.addValue(it2.getValue());
			}
			
			if(it2.nodeType().equals(NodeType.DOUBLE)) {

				it.addValue(it2.getValue());

				it2.goLeft();
				it.goLeft();

				mirrorVAux(it,it2, 1);

				it2.goUp();
				it.goUp();

				it2.goRight();
				it.goRight();
				
				
				mirrorVAux(it,it2, 1);
				
				it2.goUp();
				it.goUp();
			}
		}
		
	}

	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		int cpt = 0;
		it.clear();
		mirrorHAux(it,it2, cpt);
	}

	public void mirrorHAux(Iterator<Node> it, Iterator<Node> it2, int cpt) {
		if (cpt%2 == 1) { // on est en d�coupage horizontal
			if(it2.nodeType().equals(NodeType.LEAF)) {
				it.addValue(it2.getValue());
			}

			if(it2.nodeType().equals(NodeType.DOUBLE)) {

				it.addValue(it2.getValue());

				it2.goLeft();
				it.goRight();

				mirrorHAux(it,it2, 2);

				it2.goUp();
				it.goUp();

				it2.goRight();
				it.goLeft();
				
				
				mirrorHAux(it,it2,2);
				
				it2.goUp();
				it.goUp();
			}
			
		}
		else {
			if(it2.nodeType().equals(NodeType.LEAF)) {
				it.addValue(it2.getValue());
			}
			
			if(it2.nodeType().equals(NodeType.DOUBLE)) {

				it.addValue(it2.getValue());

				it2.goLeft();
				it.goLeft();

				mirrorHAux(it,it2, 1);

				it2.goUp();
				it.goUp();

				it2.goRight();
				it.goRight();
				
				
				mirrorHAux(it,it2, 1);
				
				it2.goUp();
				it.goUp();
			}
		}
	}

	/**
	 * this devient quart supérieur gauche de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		//on tente d'aller 2 fois a gauche pour r�cuperer le sous arbre de quart en haut � gauche
		// si on y arrive pas on affecte directement le sous-arbre 
		if (it2.nodeType().equals(NodeType.DOUBLE))it2.goLeft();
		else {affectAux(it,it2); return;}
		if (it2.nodeType().equals(NodeType.DOUBLE))it2.goLeft();
		else {affectAux(it,it2); return;}
		affectAux(it,it2);
	}
	

	/**
	 * Le quart supérieur gauche de this devient image2, le reste de this
	 * devient éteint.
	 * 
	 * @param image2
	 *            image à réduire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		
		it.addValue(Node.valueOf(2));
		it.goRight();
		it.addValue(Node.valueOf(0));
		it.goUp();
		it.goLeft();
		
		it.addValue(Node.valueOf(2));
		it.goRight();
		it.addValue(Node.valueOf(0));
		it.goUp();
		it.goLeft();

		zoomOutAux(it, it2, 0);

		if (it.getValue().state == 0){
			it.goRoot();
			it.clear();
			it.addValue(Node.valueOf(0));
		}
	
	}
	public void zoomOutAux(Iterator<Node> it, Iterator<Node> it2, int profondeur) {

		int right;
		int left;

		if (profondeur < 14) {

			if (it2.nodeType().equals(NodeType.DOUBLE)) {
				it.addValue(it2.getValue());

				it.goLeft();
				it2.goLeft();

				zoomOutAux(it, it2, profondeur + 1);

				left = it.getValue().state;

				it.goUp();
				it2.goUp();

				it.goRight();
				it2.goRight();

				zoomOutAux(it, it2, profondeur + 1);

				right = it.getValue().state;

				it.goUp();
				it2.goUp();

				if (left == right && right != 2) {
					it.clear();
					it.addValue(Node.valueOf(right));
				}

			} else it.addValue(it2.getValue());

		} else {
			if(it2.nodeType().equals(NodeType.DOUBLE)) {

				it2.goLeft();

				left = it2.getValue().state;

				it2.goUp();
				it2.goRight();

				right = it2.getValue().state;

				it2.goUp();

				if ((left == 0 && right == 2) || (left == 0 && right == 0) || (left == 2 && right == 0)){
					it.addValue(Node.valueOf(0));
				}else{
					it.addValue(Node.valueOf(1));
				}
									
			} else it.addValue(it2.getValue());
		}
	}
	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels
	 * allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {
	
		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		intersectionOuUnion(it,it1,it2,1);
		
	}
	
	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		intersectionOuUnion(it,it1,it2,0);
	}
	
	/**
	* Fonction qui fait l'intersection ou l'union (en fonction du paramètre value)
	*
	* @param it l'itérateur de this
	* @param it1 l'térateur de l'image 1
	* @param it2 l'térateur de l'image 2
	* @param value qui dit si on fait une union ou l'intersection
	* 0 -> union
	* 1 -> intersection
	*
	*
	*
	*
	*/

	public void intersectionOuUnion(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2, int valeur) {
		
		if(it1.nodeType().equals(NodeType.DOUBLE) && it2.nodeType().equals(NodeType.DOUBLE)) {

			it.addValue(Node.valueOf(2));
			
			it.goLeft();
			it1.goLeft();
			it2.goLeft();
			

			intersectionOuUnion(it,it1,it2,valeur);

			it.goUp();
			it1.goUp();
			it2.goUp();

			it.goRight();
			it1.goRight();
			it2.goRight();
			
			intersectionOuUnion(it,it1,it2,valeur);
			
			it.goUp();
			it1.goUp();
			it2.goUp();
		}
		
		else if (it1.getValue().state == 2 && it2.getValue().state == 1) 
				{
			affectAux(it,it1);
			return;
		}
		else if (it1.getValue().state == 1 && it2.getValue().state == 2) {
			affectAux(it,it2);
			return;
		}
		else if (it1.getValue().state == valeur && it2.getValue().state == valeur) {
			//it.clear();
			it.setValue(Node.valueOf(valeur));
			removeDoublons(it);
		}
		else if (it1.getValue().state == 1-valeur || it2.getValue().state == 1-valeur){
			//it.clear();
			it.setValue(Node.valueOf(1-valeur));
			removeDoublons(it);
		}
	}
	

	public void removeDoublons(Iterator<Node> it) {

		try {
			int gauche = -1;
			int droite = -1;
			it.goUp();
			it.goLeft();
			if (!it.nodeType().equals(NodeType.SENTINEL)) {
				gauche = it.getValue().state;
			}
			it.goUp();
			it.goRight();
			if (!it.nodeType().equals(NodeType.SENTINEL)) {
				droite = it.getValue().state;
			}
			it.goUp();
			if (gauche != -1 && droite != -1 && gauche == droite) {
				it.clear();
				it.addValue(Node.valueOf(gauche));
			}
		} catch (AssertionError e) {
			System.out.println("On est à la racine !");
		}
		
	}
	


	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		Iterator<Node> it = this.iterator();

        diagonalAux(it, 0,0,255,1);

		return it.getValue().state == 1;
	}
	
	private void diagonalAux(Iterator<Node> it, int x, int bas, int haut, int profondeur) {

		if (it.nodeType().equals(NodeType.DOUBLE)) {

			int milieu = (bas + haut)/2;

			if (profondeur%2 == 0) {

				if (x <= milieu) {
					it.goLeft();
					diagonalAux(it, x, bas, milieu, profondeur+1);
				}
				else {
					it.goRight();
					diagonalAux(it, x, milieu+1, haut, profondeur+1);
				}

			} else {

				if (x <= milieu) {
					it.goLeft();
				}else {
					it.goRight();
				}
				diagonalAux(it, x, bas, haut, profondeur+1);
			}
		}
		else if (it.getValue().state == 1 && x <= 255){
			it.goRoot();
			diagonalAux(it, x + 1, 0, 255, 1);
		}
	}


	/**
	 * @param x1
	 *            abscisse du premier point
	 * @param y1
	 *            ordonnée du premier point
	 * @param x2
	 *            abscisse du deuxième point
	 * @param y2
	 *            ordonnée du deuxième point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont représentés par
	 *         la même feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}

	/**
	 * @param image2
	 *            autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		Iterator<Node> it = this.iterator();

		Iterator<Node> it2 = image2.iterator();

		if (it.getValue().state == 1 ) {
			
			return it.getValue().state == it2.getValue().state;
			
		} else if(it.getValue().state == 0) {
			
			return true;
			
		} else {
		    includeAux(it, it2);
		}

		return  it.getValue().state == it2.getValue().state;
	}
	
	 public void includeAux(Iterator<Node> it1, Iterator<Node> it2) {

	        if (it1.nodeType().equals(NodeType.DOUBLE) && it2.nodeType().equals(NodeType.DOUBLE)) {

	            it1.goLeft();
	            it2.goLeft();

	            includeAux(it1, it2);

	            if (!(it1.getValue().state != 0 && it2.getValue().state == 0)) {

	                it2.goUp();
	                it1.goUp();

	                it1.goRight();
	                it2.goRight();

	                includeAux(it1, it2);

	                if (!(it1.getValue().state != 0 && it2.getValue().state == 0)) {
	                    it1.goUp();
	                    it2.goUp();
	                }
	            }

	        }
	    }

}
