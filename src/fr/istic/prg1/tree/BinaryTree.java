package fr.istic.prg1.tree;

import java.util.Stack;

import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 4.0
 * @since 2015-06-15
 * @param <T>
 *            type formel d'objet pour la classe
 *
 *            Les arbres binaires sont construits par chaînage par références
 *            pour les fils et une pile de pères.
 */
public class BinaryTree<T> {

	/**
	 * Type représentant les noeuds.
	 */
	private class Element {
		public T value;
		public Element left, right;

		public Element() {
			value = null;
			left = null;
			right = null;
		}

		public boolean isEmpty() {
			return this.left == null && this.right == null;
		}
	}

	private Element root;

	public BinaryTree() {
		this.root = new Element();
	}

	/**
	 * @return Un nouvel iterateur sur l'arbre this. Le noeud courant de
	 *         l’itérateur est positionné sur la racine de l’arbre.
	 */
	public TreeIterator iterator() {
	    return this.iterator();
	}

	/**
	 * @return true si l'arbre this est vide, false sinon
	 */
	public boolean isEmpty() {
	    return (root.left != null || root.right != null); 
	}

	/**
	 * Classe représentant les itérateurs sur les arbres binaires.
	 */
	public class TreeIterator implements Iterator<T> {
		private Element currentNode;
		private Stack<Element> stack;

		private TreeIterator() {
			stack = new Stack<Element>();
			currentNode = root;
		}

		/**
		 * L'itérateur se positionnne sur le fils gauche du noeud courant.
		 * 
		 * @pre Le noeud courant n’est pas un butoir.
		 */
		@Override
		public void goLeft() {
			stack.push(currentNode);
			currentNode = currentNode.left;
			try {
				assert !this.isEmpty() : "le butoir n'a pas de fils";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * L'itérateur se positionnne sur le fils droit du noeud courant.
		 * 
		 * @pre Le noeud courant n’est pas un butoir.
		 */
		@Override
		public void goRight() {
			stack.push(currentNode);
			currentNode = currentNode.right;
			try {
				assert !this.isEmpty() : "le butoir n'a pas de fils";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * L'itérateur se positionnne sur le père du noeud courant.
		 * 
		 * @pre Le noeud courant n’est pas la racine.
		 */
		@Override
		public void goUp() {
			currentNode = stack.pop();
			try {
				assert !stack.empty() : " la racine n'a pas de pere";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * L'itérateur se positionne sur la racine de l'arbre.
		 */
		@Override
		public void goRoot() {
			while(currentNode != root) {
				currentNode = stack.peek();
			}
		}

		/**
		 * @return true si l'iterateur est sur un sous-arbre vide, false sinon
		 */
		@Override
		public boolean isEmpty() {
		    return (currentNode.left == null && currentNode.right == null); 
		}

		/**
		 * @return Le genre du noeud courant.
		 */
		@Override
		public NodeType nodeType() {
		    if(currentNode.left == null && currentNode.right == null) {
		    	return NodeType.LEAF;
		    }else if(currentNode.left != null && currentNode.right != null) {
		    	return NodeType.DOUBLE;
		    }
		    return NodeType.SENTINEL;
		}

		/**
		 * Supprimer le noeud courant de l'arbre.
		 * 
		 * @pre Le noeud courant n'est pas un noeud double.
		 */
		@Override
		public void remove() {
			Element father = stack.peek();
			if(father.left.equals(currentNode)) {
				father.left = null;
				father = father.right;
			}else {
				father.right = null;
				father = father.left;
			}
			
			try {
				assert nodeType() != NodeType.DOUBLE : "retirer : retrait d'un noeud double non permis";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * Vider le sous–arbre référencé par le noeud courant, qui devient
		 * butoir.
		 */
		@Override
		public void clear() {
			currentNode.left = null;
			currentNode.right= null;
		}

		/**
		 * @return La valeur du noeud courant.
		 */
		@Override
		public T getValue() {
		    return currentNode.value;
		}

		/**
		 * Créer un nouveau noeud de valeur v à cet endroit.
		 * 
		 * @pre Le noeud courant est un butoir.
		 * 
		 * @param v
		 *            Valeur à ajouter.
		 */

		@Override
		public void addValue(T v) {
			currentNode.value = v;
			currentNode.left = new Element();
			currentNode.right = new Element();
			try {
				assert isEmpty() : "Ajouter : on n'est pas sur un butoir";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * Affecter la valeur v au noeud courant.
		 * 
		 * @param v
		 *            La nouvelle valeur du noeud courant.
		 */
		@Override
		public void setValue(T v) {
			currentNode.value = v;
		}

		private void ancestor(int i, int j) {
			try {
				assert !stack.empty() : "switchValue : argument trop grand";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			Element x = stack.pop();
			if (j < i) {
				ancestor(i, j + 1);
			} else {
				T v = x.value;
				x.value = currentNode.value;
				currentNode.value = v;
			}
			stack.push(x);
		}

		/**
		 * Echanger les valeurs associ�es au noeud courant et � son p�re d'ordre
		 *  i (le noeud courant reste inchang�).
		 * 
		 * @pre i>= 0 et racine est p�re du noeud courant d'ordre  >= i.
		 * 
		 * @param i
		 *            ordre du pre
		 */
		@Override
		public void switchValue(int i) {
			currentNode.value = switchAux(i,currentNode.value);
			try {
				assert i >= 0 : "switchValue : argument negatif";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			if (i > 0) {
				ancestor(i, 1);
			}
		}
		public T switchAux(int i, T j) {
			if(i>0) {
				currentNode = stack.peek();
				switchAux(i-1,j);
			}
			T temp = currentNode.value;
			currentNode.value = j;
			return temp;
		}
	}
	static void main() {
		
	}
}
