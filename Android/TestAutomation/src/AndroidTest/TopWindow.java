package AndroidTest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class TopWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	/*
	  public static void main(String[] args){
		    TopWindow topWindow = new TopWindow("JListSample");

		    /* 終了処理を変更 */
/*
		    topWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    topWindow.setSize(900, 600);
		    topWindow.setVisible(true);
		  }
*/

	TopWindow(String title){
		    setTitle(title);

		    /* JListの初期データ */
		    Vector<String> initData = new Vector<String>();
		    StringBuffer sb;
		    for (int i = 0 ; i < 10 ; i++){
		      sb = new StringBuffer();
		      sb.append("リスト項目");
		      sb.append(i);
		      sb.append("番目");
		      initData.add(new String(sb));
		    }
		    JList list = new JList(initData);

		    JScrollPane sp = new JScrollPane();
		    sp.getViewport().setView(list);
		    sp.setPreferredSize(new Dimension(300, 300));

		    JPanel p = new JPanel();
		    p.add(sp);

		    getContentPane().add(p, BorderLayout.CENTER);
		  }

}
