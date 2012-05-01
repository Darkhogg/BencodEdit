package es.darkhogg.bencodedit;

import java.awt.Component;

import javax.swing.Icon;
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
	
	private Icon getIconForValue ( BencodeTreeModel model, BencodeTreeNode value ) {
		return null;
	}
	
	private String getTextForValue ( BencodeTreeModel model, BencodeTreeNode node ) {
		Value<?> value = node.getValue();
		String prefix = node.getParent()==null ? "" : "[" + node.getIndex() + "] => ";
		
		if ( value instanceof IntegerValue ) {
			return prefix + "Int: " + ( (IntegerValue) value ).getValue();
			
		} else if ( value instanceof StringValue ) {
			return prefix + "String: \"" + ( (StringValue) value ).getStringValue() + "\"";
			
		} else if ( value instanceof ListValue ) {
			return prefix + "List (" + ( (ListValue) value ).getSize() + " elements)";
			
		} else if ( value instanceof DictionaryValue ) {
			return prefix + "Dictionary (" + ( (DictionaryValue) value ).getSize() + " elements)";
		}
		
		return "<?>";
	}
	
}
