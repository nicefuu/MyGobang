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
	JButton[][] chessBoard = new JButton[19][19];//创建18x18JButton数组
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
		this.setTitle("五子棋");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container mainContainer = this.getContentPane();//设置主容器	
		mainJSplitPane = new JSplitPane();//新建分割Pane
		this.setSize(WIDTH+200,HEIGHT);
		this.setResizable(false);
		mainJSplitPane.setDividerSize(1);//设置分割条宽度
		mainJSplitPane.setDividerLocation(700);//设置分割条位置
		mainContainer.add(mainJSplitPane);//讲JSplitPane添加到主界面
		chessPane = new JPanel();//新建棋盘容器		
		optionPane =new JPanel();//新建右方容器		
		mainJSplitPane.setLeftComponent(chessPane);//添加棋盘容器
		mainJSplitPane.setRightComponent(optionPane);//添加右方容器
		//设置选项布局
		optionPane.setLayout(new GridLayout(18,1));
		playing = new JLabel("",JLabel.CENTER);
		whosTurn = new JLabel("",JLabel.CENTER);
		whosTurn.setForeground(Color.blue);
		emptyLabel = new JLabel("",JLabel.CENTER);
		restartButton = new JButton("重新开始");
		regretButton = new JButton("悔棋");
		optionPane.add(playing);
		optionPane.add(whosTurn);
		optionPane.add(restartButton);
		optionPane.add(emptyLabel);
		optionPane.add(emptyLabel);
		optionPane.add(emptyLabel);
		optionPane.add(regretButton);
		black=new ImageIcon("src/image/black_32x32.png");//黑子Icon
		blue =new ImageIcon("src/image/blue32x32.png");
		//设置棋盘布局
		chessPane.setLayout(new GridLayout(19,19));//创建18x18布局	
		checkWin =new checkFive();
		gameOver = new JOptionPane();
		//棋盘初始化
		for(int i=0;i<ROW;i++)
		{
			for(int j=0;j<COLUMN;j++)
			{
				BlackisPut[i][j]=false;
				BlueisPut[i][j]=false;
				chessBoard[i][j]=new JButton();
				chessBoard[i][j].setMargin(new Insets(1,1,1,1));
				chessBoard[i][j].addActionListener(new simpleClickListener());
				playing.setText("游戏正在进行");
				whosTurn.setText("蓝子的回合");
				//chessBoard[i][j].setText(Integer.toString(i)+","+Integer.toString(j));
				chessPane.add(chessBoard[i][j]);
				
			}
		}
		//设置重新开始按钮的属性
		restartButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				chessNumber=0;
				for(int i=0;i<ROW;i++)
				{
					for(int j=0;j<COLUMN;j++)
					{
						playing.setText("游戏正在进行");
						whosTurn.setText("蓝子的回合");
						checkWin.restartSet();
						BlackisPut[i][j]=false;
						BlueisPut[i][j]=false;
						chessBoard[i][j].setIcon(null);
					}
				}
					
			}
		});
		//设置悔棋按钮属性
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
								whosTurn.setText("黑子的回合");
							}
							else if(chessNumber%2==1)
							{
								chessBoard[i][j].setIcon(black);
								BlackisPut[i][j]=true;
								whosTurn.setText("蓝子的回合");
							}
							lastrow=i;
							lastcolumn=j;
							chessNumber++;
							if(BlackisPut[lastrow][lastcolumn]&&checkWin.BlackisFive())
							{
								gameOver.showOptionDialog(null, "黑子WIN", "游戏结束", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);
								//
								restartButton.doClick();
							}
							else if(BlueisPut[lastrow][lastcolumn]&&checkWin.BlueisFive())
							{
								gameOver.showOptionDialog(null, "蓝子WIN", "游戏结束", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);
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
			//向上1
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
			//左上2
			for(int i=0;i<4;i++)
			{
				if(lastcolumn>i&&lastrow>i)
				{
					if(BlackisPut[lastrow-i-1][lastcolumn-i-1])
					{
						LeftUpChess++;//左上
					}
					else break;
				}
			}
			//左3
			for(int i=0;i<4;i++)
			{
				if(lastcolumn>i)
				{
					if(BlackisPut[lastrow][lastcolumn-i-1])
					{
						LeftChess++;//左
					}
					else break;
				}
			}
			//左下4
			for(int i=0;i<4;i++)
			{
				if(lastrow<18-i&&lastcolumn>i)
				{
					if(BlackisPut[lastrow+i+1][lastcolumn-i-1])
					{
						LeftDownChess++;//左
					}
					else break;
				}
			}
			//下5
			for(int i=0;i<4;i++)
			{
				if(lastrow<18-i)
				{
					if(BlackisPut[lastrow+i+1][lastcolumn])
					{
						DownChess++;//左
					}
					else break;
				}
			}
			//右下6
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
			//右7
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
			//右上8
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
			//向上1
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
			//左上2
			for(int i=0;i<4;i++)
			{
				if(lastcolumn>i&&lastrow>i)
				{
					if(BlueisPut[lastrow-i-1][lastcolumn-i-1])
					{
						LeftUpChess++;//左上
					}
					else break;
				}
			}
			//左3
			for(int i=0;i<4;i++)
			{
				if(lastcolumn>i)
				{
					if(BlueisPut[lastrow][lastcolumn-i-1])
					{
						LeftChess++;//左
					}
					else break;
				}
			}
			//左下4
			for(int i=0;i<4;i++)
			{
				if(lastrow<18-i&&lastcolumn>i)
				{
					if(BlueisPut[lastrow+i+1][lastcolumn-i-1])
					{
						LeftDownChess++;//左
					}
					else break;
				}
			}
			//下5
			for(int i=0;i<4;i++)
			{
				if(lastrow<18-i)
				{
					if(BlueisPut[lastrow+i+1][lastcolumn])
					{
						DownChess++;//左
					}
					else break;
				}
			}
			//右下6
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
			//右7
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
			//右上8
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
