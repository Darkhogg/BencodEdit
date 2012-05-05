package es.darkhogg.bencodedit;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import es.darkhogg.torrent.bencode.DictionaryValue;
import es.darkhogg.torrent.bencode.IntegerValue;
import es.darkhogg.torrent.bencode.ListValue;
import es.darkhogg.torrent.bencode.StringValue;
import es.darkhogg.torrent.bencode.Value;

public final class BencodeTreeModel implements TreeModel {
	
	private final BencodeTreeNode root;
	
	private final Set<TreeModelListener> listeners = new HashSet<>();
	
	public BencodeTreeModel ( Value<?> root ) {
		this.root = root == null ? null : new BencodeTreeNode( root, null, null );
		
		System.out.println( root );
	}
	
	@Override
	public BencodeTreeNode getRoot () {
		return root;
	}
	
	@Override
	public BencodeTreeNode getChild ( Object parent, int index ) {
		Value<?> vprt = ( (BencodeTreeNode) parent ).getValue();
		
		if ( vprt instanceof ListValue ) {
			return new BencodeTreeNode( ( (ListValue) vprt ).get( index ), vprt, index );
			
		} else if ( vprt instanceof DictionaryValue ) {
			Iterator<Map.Entry<String,Value<?>>> it = ( (DictionaryValue) vprt ).getValue().entrySet().iterator();
			for ( int i = 0; i < index; i++ ) {
				it.next();
			}
			
			Map.Entry<String,Value<?>> entry = it.next();
			return new BencodeTreeNode( entry.getValue(), vprt, '"' + entry.getKey() + '"' );
		}
		
		return null;
	}
	
	@Override
	public int getChildCount ( Object parent ) {
		Value<?> vprt = ( (BencodeTreeNode) parent ).getValue();
		if ( vprt instanceof ListValue ) {
			return ( (ListValue) vprt ).getSize();
			
		} else if ( vprt instanceof DictionaryValue ) {
			return ( (DictionaryValue) vprt ).getSize();
			
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean isLeaf ( Object node ) {
		Value<?> vnode = ( (BencodeTreeNode) node ).getValue();
		return ( vnode instanceof IntegerValue ) || ( vnode instanceof StringValue );
	}
	
	@Override
	public void valueForPathChanged ( TreePath path, Object newValue ) {
		Value<?> nval = ( (BencodeTreeNode) newValue ).getValue();
	}
	
	@Override
	public int getIndexOfChild ( Object parent, Object child ) {
		Value<?> vprt = ( (BencodeTreeNode) parent ).getValue();
		Value<?> vcld = ( (BencodeTreeNode) child ).getValue();
		
		if ( vprt instanceof ListValue ) {
			int i = 0;
			for ( Iterator<?> it = ( (ListValue) vprt ).getValue().iterator(); it.hasNext(); i++ ) {
				if ( it.next() == vcld ) {
					return i;
				}
			}
		} else if ( vprt instanceof DictionaryValue ) {
			int i = 0;
			for ( Iterator<?> it = ( (DictionaryValue) vprt ).getValue().values().iterator(); it.hasNext(); i++ ) {
				if ( it.next() == vcld ) {
					return i;
				}
			}
		}
		
		return -1;
	}
	
	@Override
	public void addTreeModelListener ( TreeModelListener lst ) {
		listeners.add( lst );
	}
	
	@Override
	public void removeTreeModelListener ( TreeModelListener lst ) {
		listeners.remove( lst );
	}
}
