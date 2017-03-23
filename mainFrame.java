package five_son_chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainFrame extends JFrame {
	static final int ROW =19;
	static final int COLUMN =19;
	static final int WIDTH =700;
	static final int HEIGHT =700;
	JLabel playing;
	JLabel whosTurn;
	JLabel emptyLabel;
	JButton restartButton;
	JButton regretButton;
	JPanel chessPane;
	JPanel optionPane;
	JSplitPane mainJSplitPane;
	JOptionPane gameOver;
	JButton[][] chessBoard = new JButton[19][19];//����18x18JButton����
	boolean BlackisPut[][]=new boolean[19][19];
	boolean BlueisPut[][]=new boolean[19][19];
	int lastrow=0;
	int lastcolumn=0;
	int chessNumber=0;
	Font labelFont;
	ImageIcon black;
	ImageIcon blue;
	checkFive checkWin;
	mainFrame(){	
		this.setTitle("������");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container mainContainer = this.getContentPane();//����������	
		mainJSplitPane = new JSplitPane();//�½��ָ�Pane
		this.setSize(WIDTH+200,HEIGHT);
		this.setResizable(false);
		mainJSplitPane.setDividerSize(1);//���÷ָ������
		mainJSplitPane.setDividerLocation(700);//���÷ָ���λ��
		mainContainer.add(mainJSplitPane);//��JSplitPane��ӵ�������
		chessPane = new JPanel();//�½���������		
		optionPane =new JPanel();//�½��ҷ�����		
		mainJSplitPane.setLeftComponent(chessPane);//�����������
		mainJSplitPane.setRightComponent(optionPane);//����ҷ�����
		//����ѡ���
		optionPane.setLayout(new GridLayout(18,1));
		playing = new JLabel("",JLabel.CENTER);
		whosTurn = new JLabel("",JLabel.CENTER);
		whosTurn.setForeground(Color.blue);
		emptyLabel = new JLabel("",JLabel.CENTER);
		restartButton = new JButton("���¿�ʼ");
		regretButton = new JButton("����");
		optionPane.add(playing);
		optionPane.add(whosTurn);
		optionPane.add(restartButton);
		optionPane.add(emptyLabel);
		optionPane.add(emptyLabel);
		optionPane.add(emptyLabel);
		optionPane.add(regretButton);
		black=new ImageIcon("src/image/black_32x32.png");//����Icon
		blue =new ImageIcon("src/image/blue32x32.png");
		//�������̲���
		chessPane.setLayout(new GridLayout(19,19));//����18x18����	
		checkWin =new checkFive();
		gameOver = new JOptionPane();
		//���̳�ʼ��
		for(int i=0;i<ROW;i++)
		{
			for(int j=0;j<COLUMN;j++)
			{
				BlackisPut[i][j]=false;
				BlueisPut[i][j]=false;
				chessBoard[i][j]=new JButton();
				chessBoard[i][j].setMargin(new Insets(1,1,1,1));
				chessBoard[i][j].addActionListener(new simpleClickListener());
				playing.setText("��Ϸ���ڽ���");
				whosTurn.setText("���ӵĻغ�");
				//chessBoard[i][j].setText(Integer.toString(i)+","+Integer.toString(j));
				chessPane.add(chessBoard[i][j]);
				
			}
		}
		//�������¿�ʼ��ť������
		restartButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				chessNumber=0;
				for(int i=0;i<ROW;i++)
				{
					for(int j=0;j<COLUMN;j++)
					{
						playing.setText("��Ϸ���ڽ���");
						whosTurn.setText("���ӵĻغ�");
						checkWin.restartSet();
						BlackisPut[i][j]=false;
						BlueisPut[i][j]=false;
						chessBoard[i][j].setIcon(null);
					}
				}
					
			}
		});
		//���û��尴ť����
		regretButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(chessNumber>0)
					{
					chessNumber--;
					chessBoard[lastrow][lastcolumn].setIcon(null);
					BlackisPut[lastrow][lastcolumn]=false;
					BlueisPut[lastrow][lastcolumn]=false;
					}
			}
		});
		

		this.setVisible(true);
	
	}
	
	public static void main(String[] args) {
		new mainFrame();
		//checkFive check = new checkFive();
	}
	
	class simpleClickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			for(int i=0;i<ROW;i++) 
			{
				for(int j=0;j<COLUMN;j++)
				{
					if(e.getSource()==chessBoard[i][j])
					{
						if(!BlackisPut[i][j]&&!BlueisPut[i][j]){
							if(chessNumber%2==0)
							{
								chessBoard[i][j].setIcon(blue);
								BlueisPut[i][j]=true;
								whosTurn.setText("���ӵĻغ�");
							}
							else if(chessNumber%2==1)
							{
								chessBoard[i][j].setIcon(black);
								BlackisPut[i][j]=true;
								whosTurn.setText("���ӵĻغ�");
							}
							lastrow=i;
							lastcolumn=j;
							chessNumber++;
							if(BlackisPut[lastrow][lastcolumn]&&checkWin.BlackisFive())
							{
								gameOver.showOptionDialog(null, "����WIN", "��Ϸ����", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);
								//
								restartButton.doClick();
							}
							else if(BlueisPut[lastrow][lastcolumn]&&checkWin.BlueisFive())
							{
								gameOver.showOptionDialog(null, "����WIN", "��Ϸ����", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);
								restartButton.doClick();
							}
						}
					}
				}
			}
		}
	}
	class checkFive
	{
		private int LeftChess;
		private int RightChess;
		private int UpChess;
		private int DownChess;
		private int LeftUpChess;
		private int RightUpChess;
		private int LeftDownChess;
		private int RightDownChess;
		checkFive(){
			this.LeftChess=0;
			this.RightChess=0;
			this.UpChess=0;
			this.DownChess=0;
			this.LeftUpChess=0;
			this.RightUpChess=0;
			this.LeftDownChess=0;
			this.RightDownChess=0;
		}
		public void restartSet()
		{
			LeftChess=0;
			RightChess=0;
			UpChess=0;
			DownChess=0;
			LeftUpChess=0;
			RightUpChess=0;
			LeftDownChess=0;
			RightDownChess=0;
		}
		boolean BlackisFive()		
		{
			//����1
			for(int i=0;i<4;i++)
			{
				if(lastrow>i)
				{
					if(BlackisPut[lastrow-i-1][lastcolumn])
					{
						UpChess++;
					}
					else break;
				}
			}
			//����2
			for(int i=0;i<4;i++)
			{
				if(lastcolumn>i&&lastrow>i)
				{
					if(BlackisPut[lastrow-i-1][lastcolumn-i-1])
					{
						LeftUpChess++;//����
					}
					else break;
				}
			}
			//��3
			for(int i=0;i<4;i++)
			{
				if(lastcolumn>i)
				{
					if(BlackisPut[lastrow][lastcolumn-i-1])
					{
						LeftChess++;//��
					}
					else break;
				}
			}
			//����4
			for(int i=0;i<4;i++)
			{
				if(lastrow<18-i&&lastcolumn>i)
				{
					if(BlackisPut[lastrow+i+1][lastcolumn-i-1])
					{
						LeftDownChess++;//��
					}
					else break;
				}
			}
			//��5
			for(int i=0;i<4;i++)
			{
				if(lastrow<18-i)
				{
					if(BlackisPut[lastrow+i+1][lastcolumn])
					{
						DownChess++;//��
					}
					else break;
				}
			}
			//����6
			for(int i=0;i<4;i++)
			{
				if(lastrow+i<18&&lastcolumn+i<18)
				{
					if(BlackisPut[lastrow+i+1][lastcolumn+i+1])
					{
						RightDownChess++;
					}
					else break;
				}
			}
			//��7
			for(int i=0;i<4;i++)
			{
				if(lastcolumn<18-i)
				{
					if(BlackisPut[lastrow][lastcolumn+i+1])
					{
						RightChess++;
					}
					else break;
				}
			}
			//����8
			for(int i=0;i<4;i++)
			{
				if(lastrow>i&&lastcolumn<18-i)
				{
					if(BlackisPut[lastrow-i-1][lastcolumn+i+1])
					{
						RightUpChess++;
					}
					else break;
				}
			}
			
			if(UpChess+DownChess>=4
				||RightChess+LeftChess>=4
				||RightUpChess+LeftDownChess>=4
				||RightDownChess+LeftUpChess>=4)
			{
				
				return true;
			}
			else 
			{
				restartSet();
				return false;
			}
		}
		boolean BlueisFive()
		{
			//����1
			for(int i=0;i<4;i++)
			{
				if(lastrow>i)
				{
					if(BlueisPut[lastrow-i-1][lastcolumn])
					{
						UpChess++;
					}
					else break;
				}
			}
			//����2
			for(int i=0;i<4;i++)
			{
				if(lastcolumn>i&&lastrow>i)
				{
					if(BlueisPut[lastrow-i-1][lastcolumn-i-1])
					{
						LeftUpChess++;//����
					}
					else break;
				}
			}
			//��3
			for(int i=0;i<4;i++)
			{
				if(lastcolumn>i)
				{
					if(BlueisPut[lastrow][lastcolumn-i-1])
					{
						LeftChess++;//��
					}
					else break;
				}
			}
			//����4
			for(int i=0;i<4;i++)
			{
				if(lastrow<18-i&&lastcolumn>i)
				{
					if(BlueisPut[lastrow+i+1][lastcolumn-i-1])
					{
						LeftDownChess++;//��
					}
					else break;
				}
			}
			//��5
			for(int i=0;i<4;i++)
			{
				if(lastrow<18-i)
				{
					if(BlueisPut[lastrow+i+1][lastcolumn])
					{
						DownChess++;//��
					}
					else break;
				}
			}
			//����6
			for(int i=0;i<4;i++)
			{
				if(lastrow<18-i&&lastcolumn<18-i)
				{
					if(BlueisPut[lastrow+i+1][lastcolumn+i+1])
					{
						RightDownChess++;
					}
					else break;
				}
			}
			//��7
			for(int i=0;i<4;i++)
			{
				if(lastcolumn<18-i)
				{
					if(BlueisPut[lastrow][lastcolumn+i+1])
					{
						RightChess++;
					}
					else break;
				}
			}
			//����8
			for(int i=0;i<4;i++)
			{
				if(lastrow>i&&lastcolumn<18-i)
				{
					if(BlueisPut[lastrow-i-1][lastcolumn+i+1])
					{
						RightUpChess++;
					}
					else break;
				}
			}
			
			if(UpChess+DownChess>=4
				||RightChess+LeftChess>=4
				||RightUpChess+LeftDownChess>=4
				||RightDownChess+LeftUpChess>=4)
			{
				return true;
			}
			else 
			{
				restartSet();
				return false;
			}
		}

	}
}
