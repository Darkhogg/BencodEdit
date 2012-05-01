package es.darkhogg.bencodedit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

public final class BencodEdit {
	
	public static final String MAIN_FRAME_TITLE = "Bencode Editor";
	
	private static void setupLookAndFeel () {
		try {
			// UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
			UIManager.setLookAndFeel( new Plastic3DLookAndFeel() );
		} catch ( Exception e ) {
			// Do nothing
		}
	}
	
	private static void launchGui () {
		SwingUtilities.invokeLater( new Runnable() {
			
			@Override
			public void run () {
				JFrame frame = new BencodEditFrame();
				frame.setLocationRelativeTo( null );
				frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
				frame.setVisible( true );
			}
		} );
	}
	
	public static void main ( String[] args ) {
		setupLookAndFeel();
		launchGui();
	}
	
}
