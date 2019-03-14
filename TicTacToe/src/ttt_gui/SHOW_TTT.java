package ttt_gui;


public class SHOW_TTT {

	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run() {
				TTT_GUI gui = new TTT_GUI();//here is the magic
			}
		});

	}

}
