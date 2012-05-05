package es.darkhogg.bencodedit;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;

import es.darkhogg.torrent.bencode.DictionaryValue;
import es.darkhogg.torrent.bencode.IntegerValue;
import es.darkhogg.torrent.bencode.ListValue;
import es.darkhogg.torrent.bencode.StringValue;
import es.darkhogg.torrent.bencode.Value;

public final class BencodeTreeCellRenderer extends DefaultTreeCellRenderer {
	
	private static final long serialVersionUID = 2006775875120987810L;
	
	private static final Icon ICON_ROOT;
	private static final Icon ICON_DICTIONARY;
	private static final Icon ICON_LIST;
	private static final Icon ICON_INTEGER;
	private static final Icon ICON_STRING;
	private static final Icon ICON_BINARY;
	static {
		Class<?> cls = BencodeTreeCellRenderer.class;
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		ICON_ROOT = new ImageIcon( tk.getImage( cls.getResource( "/img/tree_root.png" ) ) );
		ICON_DICTIONARY = new ImageIcon( tk.getImage( cls.getResource( "/img/tree_dictionary.png" ) ) );
		ICON_LIST = new ImageIcon( tk.getImage( cls.getResource( "/img/tree_list.png" ) ) );
		ICON_INTEGER = new ImageIcon( tk.getImage( cls.getResource( "/img/tree_integer.png" ) ) );
		ICON_STRING = new ImageIcon( tk.getImage( cls.getResource( "/img/tree_string.png" ) ) );
		ICON_BINARY = new ImageIcon( tk.getImage( cls.getResource( "/img/tree_binary.png" ) ) );
	}
	
	@Override
	public Component getTreeCellRendererComponent (
		JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus )
	{
		JLabel label = (JLabel) super.getTreeCellRendererComponent( tree, value, sel, expanded, leaf, row, hasFocus );
		TreeModel model = tree.getModel();
		
		if ( model instanceof BencodeTreeModel && value instanceof BencodeTreeNode ) {
			label.setIcon( getIconForValue( (BencodeTreeModel) model, (BencodeTreeNode) value ) );
			label.setText( getTextForValue( (BencodeTreeModel) model, (BencodeTreeNode) value ) );
		}
		
		return label;
	}
	
	private static Icon getIconForValue ( BencodeTreeModel model, BencodeTreeNode node ) {
		if ( node.getParent() == null ) {
			return ICON_ROOT;
		}
		
		Value<?> value = node.getValue();
		
		if ( value instanceof IntegerValue ) {
			return ICON_INTEGER;
			
		} else if ( value instanceof StringValue ) {
			if ( ( (StringValue) value ).isValidUtf8() ) {
				return ICON_STRING;
			} else {
				return ICON_BINARY;
			}
			
		} else if ( value instanceof ListValue ) {
			return ICON_LIST;
			
		} else if ( value instanceof DictionaryValue ) {
			return ICON_DICTIONARY;
		}
		
		return null;
	}
	
	private static String getTextForValue ( BencodeTreeModel model, BencodeTreeNode node ) {
		Value<?> value = node.getValue();
		String prefix =
			"<html><b>" + ( node.getParent() == null ? "(Root)" : "[" + node.getIndex() + "]" ) + "</b> => ";
		
		if ( value instanceof IntegerValue ) {
			return prefix + "Int: " + ( (IntegerValue) value ).getValue();
			
		} else if ( value instanceof StringValue ) {
			if ( ( (StringValue) value ).isValidUtf8() ) {
				return prefix + "String: \"" + ( (StringValue) value ).getStringValue() + "\"";
			} else {
				return prefix + "String: &lt;Binary&gt;";
			}
			
		} else if ( value instanceof ListValue ) {
			return prefix + "List (" + ( (ListValue) value ).getSize() + " elements)";
			
		} else if ( value instanceof DictionaryValue ) {
			return prefix + "Dictionary (" + ( (DictionaryValue) value ).getSize() + " elements)";
		}
		
		return "<?>";
	}
	
}
