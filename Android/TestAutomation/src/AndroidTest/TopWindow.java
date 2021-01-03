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

		    /* �I��������ύX */
/*
		    topWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    topWindow.setSize(900, 600);
		    topWindow.setVisible(true);
		  }
*/

	TopWindow(String title){
		    setTitle(title);

		    /* JList�̏����f�[�^ */
		    Vector<String> initData = new Vector<String>();
		    StringBuffer sb;
		    for (int i = 0 ; i < 10 ; i++){
		      sb = new StringBuffer();
		      sb.append("���X�g����");
		      sb.append(i);
		      sb.append("�Ԗ�");
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
