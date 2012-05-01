package es.darkhogg.bencodedit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import es.darkhogg.torrent.bencode.BencodeInputStream;
import es.darkhogg.torrent.bencode.Value;

public final class BencodEditFrame extends JFrame {
	
	private final JFileChooser fileChooser = new JFileChooser( System.getProperty( "user.home" ) );
	
	private JTree tree = null;
	
	public BencodEditFrame () {
		super( BencodEdit.MAIN_FRAME_TITLE );
		
		setupGui();
		
		pack();
	}
	
	private void setupGui () {
		setupMenu();
		setupToolbar();
		setupTree();
	}
	
	private void setupMenu () {
		JMenuBar menuBar = new JMenuBar();
		
		{ // File Menu
			JMenu fileMenu = new JMenu( "File" );
			
			{ // Open Item
				JMenuItem openItem = new JMenuItem( "Open..." );
				openItem.addActionListener( new ActionListener() {
					
					@Override
					public void actionPerformed ( ActionEvent e ) {
						actionOpenFile();
					}
				} );
				fileMenu.add( openItem );
			}
			
			{ // Save Item
				JMenuItem saveItem = new JMenuItem( "Save" );
				fileMenu.add( saveItem );
			}
			
			{ // Save As Item
				JMenuItem saveAsItem = new JMenuItem( "Save As..." );
				fileMenu.add( saveAsItem );
			}
			
			menuBar.add( fileMenu );
		}
		
		setJMenuBar( menuBar );
	}
	
	private void actionOpenFile () {
		int res = fileChooser.showOpenDialog( this );
		if ( res == JFileChooser.APPROVE_OPTION ) {
			File file = fileChooser.getSelectedFile();
			try ( BencodeInputStream bin = new BencodeInputStream( file ) ) {
				Value<?> val = bin.readValue();
				tree.setModel( new BencodeTreeModel( val ) );
				
			} catch ( IOException exc ) {
				// TODO Auto-generated catch block
				exc.printStackTrace();
			}
			
		}
	}
	
	private void setupToolbar () {
		// TODO Auto-generated method stub
		
	}
	
	private void setupTree () {
		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout( 4, 4 ) );
		panel.setBorder( BorderFactory.createEmptyBorder( 4, 4, 4, 4 ) );
		
		{ // Bencode Tree
			tree = new JTree( new BencodeTreeModel( null ) );
			tree.setCellRenderer( new BencodeTreeCellRenderer() );
			tree.setShowsRootHandles( true );
			
			JScrollPane scrollPane = new JScrollPane( tree );
			panel.add( scrollPane, BorderLayout.CENTER );
		}
		
		setContentPane( panel );
	}
}
