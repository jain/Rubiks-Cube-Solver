   import java.awt.*;
   import java.awt.event.*;
   import javax.swing.*;
   import java.util.*;
   public class RubixPanel extends JPanel{
      private int arr[][][];
      private JTextField panArr[][][];
      private JButton solve;
      private int obj[][];
      private JButton test;
      private Object undo[][];
      private int undocount;
      private boolean move;
      private JButton next;
      private int copy[][][];
      private int total;
      private boolean change;
      public RubixPanel(){
         total = 0;
         move = true;
         undocount = 0;
         setLayout(new GridLayout(4, 4));
         panArr = new JTextField[6][3][3];
         arr = new int[6][3][3];
         copy = new int[6][3][3];
         obj = new int[9][12];
         adder();
         undo = new Object[10000][2];
         setBorder(BorderFactory.createLineBorder(Color.black));
         solve = new JButton("Solve");
         test = new JButton("Test");
         next = new JButton("Next");
         add(solve);
         add(test);
         add(next);
         solve.addActionListener(new SolveListener());   
         test.addActionListener(new TestListener());
         change = true;	
      }
      public void align(){
         moves(5, false);
         moves(8, true);
         moves(5, true);
         moves(8, false);
      }
      public void finale(){
         int[] perfect = {28, 33, 31, 26};
         int num = 0;
         while(!(arr[4][0][2]==26&&arr[4][0][0]==28&&arr[4][2][2]==31&&arr[4][2][0]==33)){
            changeFace(0, 4);
            while(arr[0][2][2]!=perfect[num]){
               align();
            }
            if(num==3){
               while(!(arr[0][0][2]==33&&arr[0][0][0]==31&&arr[0][2][2]==28&&arr[0][2][0]==26)){
                  moves(6, true);
               }
            }
            else{
               moves(6, true);
               num++;
            }
            move = false;
            change = false;
            white();
            move = true;
            change = true;
         }
         System.out.println("Done");
      }
      public void correctCorner(){
         moves(6, true);
         moves(5, true);
         moves(6, false);
         moves(3, true);
         moves(6, true);
         moves(5, false);
         moves(6, false);
         moves(3, false);
      }
      public void alignCorners(){
       /*1+0, 26
      	0+3, 28
      	5+3, 33
      	1+5, 31*/
         boolean condition1 = arr[0][0][0]==26||arr[1][0][0]==26||arr[4][0][2]==26;
         boolean condition2 = arr[0][0][2]==28||arr[3][0][2]==28||arr[4][0][0]==28;
         boolean condition3 = arr[5][2][0]==31||arr[1][2][0]==31||arr[4][2][2]==31;
         boolean condition4 = arr[5][2][2]==33||arr[3][2][2]==33||arr[4][2][0]==33;
         boolean condition = condition1&&condition2&&condition3&&condition4;
         boolean face = true;
         int num = 0;
         while(!condition){
            if((condition1&&face)||num ==1){
               changeFace(1, 4);
               face = false;
               num = 1;
            } 
            else if((condition2&&face)||num ==2){
               changeFace(0, 4);
               face = false;
               num = 2;
            } 
            else if((condition3&&face)||num ==3){
               changeFace(5, 4);
               face = false;
               num = 3;
            } 
            else if((condition4&&face)||num ==4){
               changeFace(3, 4);
               face = false;
               num = 4;
            } 
            else if (face){
               changeFace(1, 4);
            }
            correctCorner();
            move = false;
            change = false;
            white();
            move = true;
            change = true;
            condition1 = arr[0][0][0]==26||arr[1][0][0]==26||arr[4][0][2]==26;
            condition2 = arr[0][0][2]==28||arr[3][0][2]==28||arr[4][0][0]==28;
            condition3 = arr[5][2][0]==31||arr[1][2][0]==31||arr[4][2][2]==31;
            condition4 = arr[5][2][2]==33||arr[3][2][2]==33||arr[4][2][0]==33;
            condition = condition1&&condition2&&condition3&&condition4;
         }
      }
      public void alignCross(){
         moves(5, true);
         moves(6, true);
         moves(5, false);
         moves(6, true);
         moves(5, true);
         moves(6, true);
         moves(6, true);
         moves(5, false);
         moves(6, true);
      }
      public void correctLastCross(){
         int perfect[] = {0, 3, 5, 1};
         int sum = 0;
         for (int i = 1; i<12; i+=3){
            if(perfect[(i-1)/3] == obj[0][i]){
               sum++;
            }
         }
         while(sum<2){
            moves(0, true);
            sum = 0;
            for (int i = 1; i<12; i+=3){
               if(perfect[(i-1)/3] == obj[0][i]){
                  sum++;
               }
            }
         }
         while(!(obj[0][1] ==0 && obj[0][4] ==3 && obj[0][7] == 5 && obj[0][10] == 1)){
            if(obj[0][1] == 0 && obj[0][7] == 5){
               changeFace(0, 4);
               alignCross();
            }
            else if(obj[0][1] == 0 && obj[0][7] == 5){
               changeFace(0, 3);
               alignCross();
            } 
            else{
               changeFace(0, 4);
               while(!(obj[6][1]!= obj[7][1] && obj[6][4]==obj[7][4])){
                  move = false;
                  cubeRight();
                  System.out.println("Move the cube right");
                  move = true;
               }
               alignCross();
            }
            move = false;
            change = false;
            white();
            move = true;
            change = true;
         }
      }
      public void stepOne(){
         moves(2, false);
         moves(5, true);
         moves(6, true);
         moves(5, false);
         moves(6, false);
         moves(2, true);
      }
      public void stepTwo(){
         moves(2, false);
         moves(6, true);
         moves(5, true);
         moves(6, false);
         moves(5, false);
         moves(2, true);
      }
      public void lastCross(){
         while(!(arr[4][0][1] == 4 && arr[4][1][0] == 4 && arr[4][1][2] == 4 && arr[4][2][1] == 4)){
            if(!(arr[4][0][1] == 4 || arr[4][1][0] == 4 || arr[4][1][2] == 4 || arr[4][2][1] == 4)){
               changeFace(0, 4);
               stepOne();
            } 
            else{
               changeFace(0, 4);
               while(arr[0][1][0] !=4){
                  moves(6, true);
               }
               if(arr[0][1][2] == 4){
                  stepOne();
               } 
               else{
                  while(arr[0][0][1] !=4){
                     moves(6, true);
                  }
                  stepTwo();
               }
            }
            move = false;
            change = false;
            white();
            move = true;
            change = true;
            print();
         }
      }
      public void secondRight(){
         moves(6, true);
         moves(5, true);
         moves(6, false);
         moves(5, false);
         moves(6, false);
         moves(2, true);
         moves(6, true);
         moves(2, false);
      }
      public void secondLeft(){
         moves(6, false);
         moves(3, true);
         moves(6, true);
         moves(3, false);
         moves(6, true);
         moves(2, false);
         moves(6, false);
         moves(2, true);
      }
      public void secondLayer(){
         int[] model = {0, 0, 0, 3, 3, 3, 5, 5, 5, 1, 1, 1};
         while(!Arrays.equals(model, obj[1])){
            if(obj[4][8] != 4 && obj[4][9]!=4){
               int num = obj[4][8];
               int num2 = obj[4][9];
               changeFace(num2, 4);
               while(!(arr[2][0][1]==num2&&arr[0][2][1]==num)){
                  moves(6, true);
               }
               if(arr[3][1][1] == num){
                  secondRight();
               } 
               else{
                  secondLeft();
               }
            }
            else if(obj[4][6]!=4&&obj[4][5] !=4){
               int num = obj[4][6];
               int num2 = obj[4][5];
               changeFace(num2, 4);
               while(!(arr[2][0][1]==num2&&arr[0][2][1]==num)){
                  moves(6, true);
               }
               if(arr[3][1][1] == num){
                  secondRight();
               } 
               else{
                  secondLeft();
               }
            }
            else if(obj[7][8] != 4 && obj[7][9]!=4){
               int num = obj[7][8];
               int num2 = obj[7][9];
               changeFace(num2, 4);
               while(!(arr[2][0][1]==num2&&arr[0][2][1]==num)){
                  moves(6, true);
               }
               if(arr[3][1][1] == num){
                  secondRight();
               } 
               else{
                  secondLeft();
               }
            }
            else if(obj[7][6]!=4&&obj[7][5] !=4){
               int num = obj[7][6];
               int num2 = obj[7][5];
               changeFace(num2, 4);
               while(!(arr[2][0][1]==num2&&arr[0][2][1]==num)){
                  moves(6, true);
               }
               if(arr[3][1][1] == num){
                  secondRight();
               } 
               else{
                  secondLeft();
               }
            }
            else if(obj[1][0] !=0){
               changeFace(0, 4);
               secondLeft();
            }
            else if(obj[1][2] !=0){
               changeFace(0, 4);
               secondRight();
            }
            else if(obj[1][3] != 3){
               changeFace(3, 4);
               secondLeft();
            }
            else if(obj[1][5] !=3){
               changeFace(3, 4);
               secondRight();
            }
            else if(obj[1][6] != 5){
               changeFace(5, 4);
               secondLeft();
            }
            else if(obj[1][8] != 5){
               changeFace(5, 4);
               secondRight();
            }
            else if(obj[1][9] != 1){
               changeFace(1, 4);
               secondLeft();
            }
            else if(obj[1][11] != 11){
               changeFace(1, 4);
               secondRight();
            }
         	
            move = false;
            change = false;
            white();
            move = true;
            change = true;
         }
      }
      public void corners(){
         int corners[][][] = new int[8][2][3];
         for (int i = 0; i<8; i++){
            corners[i][1][0] = -1;
            corners[i][1][1] = -1;
            corners[i][1][2] = -1;
         }
         int track = 0;
         for(int y = 0; y<3; y+=2){
            for(int z = 0; z<3; z+=2){
               corners[track][0][0] = arr[0][y][z];
               if(corners[track][0][0] == 2 || corners[track][0][0] == 4){
                  corners[track][1][0] = 0;
                  corners[track][1][1] = y;
                  corners[track][1][2] = z;
               }
               if(track == 0){
                  corners[track][0][1] = arr[1][0][0];
                  corners[track][0][2] = arr[4][0][2];
                  if(corners[track][0][1] == 2 || corners[track][0][1] == 4){
                     corners[track][1][0] = 1;
                     corners[track][1][1] = 0;
                     corners[track][1][2] = 0;
                  } 
                  else if(corners[track][0][2] == 2 || corners[track][0][2] == 4){
                     corners[track][1][0] = 4;
                     corners[track][1][1] = 0;
                     corners[track][1][2] = 2;
                  }
               } 
               else if(track == 2){
                  corners[track][0][1] = arr[2][0][0];
                  corners[track][0][2] = arr[1][0][2];
                  if( corners[track][0][1] == 2||corners[track][0][1] == 4){
                     corners[track][1][0] = 2;
                     corners[track][1][1] = 0;
                     corners[track][1][2] = 0;
                  } 
                  else if(corners[track][0][2] == 2||corners[track][0][2] == 4){
                     corners[track][1][0] = 1;
                     corners[track][1][1] = 0;
                     corners[track][1][2] = 2;
                  }
               } 
               else if(track == 1){
                  corners[track][0][1] = arr[4][0][0];
                  corners[track][0][2] = arr[3][0][2];
                  if(corners[track][0][1] == 2||corners[track][0][1] == 4){
                     corners[track][1][0] = 4;
                     corners[track][1][1] = 0;
                     corners[track][1][2] = 0;
                  } 
                  else if(corners[track][0][2] == 2||corners[track][0][2] == 4){
                     corners[track][1][0] = 3;
                     corners[track][1][1] = 0;
                     corners[track][1][2] = 2;
                  }
               } 
               else{
                  corners[track][0][1] = arr[3][0][0];
                  corners[track][0][2] = arr[2][0][2];
                  if( corners[track][0][1] == 2||corners[track][0][1] == 4){
                     corners[track][1][0] = 3;
                     corners[track][1][1] = 0;
                     corners[track][1][2] = 0;
                  } 
                  else if(corners[track][0][2] == 2||corners[track][0][2] == 4){
                     corners[track][1][0] = 2;
                     corners[track][1][1] = 0;
                     corners[track][1][2] = 2;
                  }
               }
               track++;
            }
         }
         for(int y = 0; y<3; y+=2){
            for(int z = 0; z<3; z+=2){
               corners[track][0][0] = arr[5][y][z];
               if( corners[track][0][0] == 2||corners[track][0][0] == 4){
                  corners[track][1][0] = 5;
                  corners[track][1][1] = y;
                  corners[track][1][2] = z;
               }
               if(track == 4){
                  corners[track][0][1] = arr[1][2][2];
                  corners[track][0][2] = arr[2][2][0];
                  if( corners[track][0][1] == 2||corners[track][0][1] == 4){
                     corners[track][1][0] = 1;
                     corners[track][1][1] = 2;
                     corners[track][1][2] = 2;
                  } 
                  else if(corners[track][0][2] == 2||corners[track][0][2] == 4){
                     corners[track][1][0] = 2;
                     corners[track][1][1] = 2;
                     corners[track][1][2] = 0;
                  }
               } 
               else if(track == 6){
                  corners[track][0][1] = arr[4][2][2];
                  corners[track][0][2] = arr[1][2][0];
                  if( corners[track][0][1] == 2||corners[track][0][1] == 4){
                     corners[track][1][0] = 4;
                     corners[track][1][1] = 2;
                     corners[track][1][2] = 2;
                  } 
                  else if(corners[track][0][2] == 2||corners[track][0][2] == 4){
                     corners[track][1][0] = 1;
                     corners[track][1][1] = 2;
                     corners[track][1][2] = 0;
                  }
               } 
               else if(track == 5){
                  corners[track][0][1] = arr[2][2][2];
                  corners[track][0][2] = arr[3][2][0];
                  if( corners[track][0][1] == 2||corners[track][0][1] == 4){
                     corners[track][1][0] = 2;
                     corners[track][1][1] = 2;
                     corners[track][1][2] = 2;
                  } 
                  else if(corners[track][0][2] == 2||corners[track][0][2] == 4){
                     corners[track][1][0] = 3;
                     corners[track][1][1] = 2;
                     corners[track][1][2] = 0;
                  }
               } 
               else{
                  corners[track][0][1] = arr[3][2][2];
                  corners[track][0][2] = arr[4][2][0];
                  if( corners[track][0][1] == 2||corners[track][0][1] == 4){
                     corners[track][1][0] = 3;
                     corners[track][1][1] = 2;
                     corners[track][1][2] = 2;
                  } 
                  else if(corners[track][0][2] == 2||corners[track][0][2] == 4){
                     corners[track][1][0] = 4;
                     corners[track][1][1] = 2;
                     corners[track][1][2] = 0;
                  }
               }
               track++;
            }
         }
         for(int i = 0; i <8; i++){
            System.out.println(corners[i][1][0] + "+" + 
               corners[i][1][1]  + "+" + 
               corners[i][1][2]);
            System.out.println(corners[i][0][0] + "+" + 
               corners[i][0][1]  + "+" + 
               corners[i][0][2]);
         }
         for(int i = 0; i<8; i++){
            int num1 = corners[i][0][0];
            int num2 = corners[i][0][1];
            int num3 = corners[i][0][2];
            int x = corners[i][1][0];
            int y = corners[i][1][1];
            int z = corners[i][1][2];
            if (num1 ==2){
               arr[x][y][z] = num2+num3 + 5;
               autoUpdate();
            } 
            else if(num2 ==2){
               arr[x][y][z] = num1+num3 + 5;
               autoUpdate();
            } 
            else if(num3 ==2){
               arr[x][y][z] = num1+num2 + 5;
               autoUpdate();
            }
            if (num1 ==4){
               arr[x][y][z] = num2+num3 + 25;
               autoUpdate();
            } 
            else if(num2 ==4){
               arr[x][y][z] = num1+num3 + 25;
               autoUpdate();
            } 
            else if(num3 ==4){
               arr[x][y][z] = num1+num2 + 25;
               autoUpdate();
            }
         }
       /*1+0, 6
      	0+3, 8
      	5+3, 13
      	1+5, 11*/
         print();
         while(arr[2][0][0] != 6){
            if(arr[2][0][2] == 6){
               moves(6, false);
               moves(0, false);
               moves(6, true);
            }
            else if (arr[2][2][2] ==6){
               moves(5, false);
               moves(0, false);
               moves(5, true);
            }
            else if (arr[2][2][0] == 6){
               moves(8, true);
               moves(0, false);
               moves(8, false);
            } 
            else if(obj[0][0] == 6 ||obj[0][3] == 6 || obj[0][6] == 6 ||obj[0][9] == 6){
               while(obj[0][3] != 6){
                  moves(0, true);
               }
               moves(3, true);
               moves(0, true);
               moves(3, false);
            } 
            else if(obj[0][2] == 6 ||obj[0][5] == 6 || obj[0][8] == 6 ||obj[0][11] == 6){
               while(obj[0][8] != 6){
                  moves(0, true);
               }
               moves(6, true);
               moves(0, false);
               moves(6, false);
            }
            else if(obj[2][0] == 6 || obj[2][11] == 6){
               moves(6, true);
               moves(0, true);
               moves(6, false);
            }
            else if(obj[2][2] == 6 || obj[2][3] == 6){
               moves(6, false);
               moves(0, false);
               moves(6, true);
            }
            else if(obj[2][5] == 6 || obj[2][6] == 6){
               moves(8, false);
               moves(0, true);
               moves(8, true);
            }
            else if(obj[2][8] == 6 || obj[2][9] == 6){
               moves(8, true);
               moves(0, false);
               moves(8, false);
            }
            else{
               while(arr[4][0][2] !=6){
                  moves(0, true);
               }
               moves(6, true);
               moves(0, false);
               moves(6, false);
            }
         }
         while(arr[2][0][2] != 8){
            if (arr[2][2][2] ==8){
               moves(5, false);
               moves(0, false);
               moves(5, true);
            }
            else if (arr[2][2][0] == 8){
               moves(8, true);
               moves(0, false);
               moves(8, false);
            } 
            else if(obj[0][0] == 8 ||obj[0][3] == 8 || obj[0][6] ==8 ||obj[0][9] == 8){
               while(obj[0][6] != 8){
                  moves(0, true);
               }
               moves(6, false);
               moves(0, true);
               moves(6, true);
            } 
            else if(obj[0][2] == 8 ||obj[0][5] == 8 || obj[0][8] == 8 ||obj[0][11] == 8){
               while(obj[0][11] != 8){
                  moves(0, true);
               }
               moves(5, true);
               moves(0, false);
               moves(5, false);
            }
            else if(obj[2][2] == 8 || obj[2][3] == 8){
               moves(6, false);
               moves(0, false);
               moves(6, true);
            }
            else if(obj[2][5] == 8 || obj[2][6] == 8){
               moves(8, false);
               moves(0, true);
               moves(8, true);
            }
            else if(obj[2][8] == 8 || obj[2][9] == 8){
               moves(8, true);
               moves(0, false);
               moves(8, false);
            }
            else{
               while(arr[4][0][0] !=8){
                  moves(0, true);
               }
               moves(6, false);
               moves(0, true);
               moves(6, true);
            }
         }
         while(arr[2][2][0] != 11){
            if (arr[2][2][2] ==11){
               moves(5, false);
               moves(0, false);
               moves(5, true);
            }
            else if(obj[0][0] == 11 ||obj[0][3] == 11 || obj[0][6] ==11 ||obj[0][9] == 11){
               while(obj[0][0] != 11){
                  moves(0, true);
               }
               moves(8, true);
               moves(0, true);
               moves(8, false);
            } 
            else if(obj[0][2] == 11 ||obj[0][5] == 11 || obj[0][8] == 11 ||obj[0][11] == 11){
               while(obj[0][5] != 11){
                  moves(0, true);
               }
               moves(3, false);
               moves(0, false);
               moves(3, true);
            }
            else if(obj[2][5] == 11 || obj[2][6] == 11){
               moves(8, false);
               moves(0, true);
               moves(8, true);
            }
            else if(obj[2][8] == 11 || obj[2][9] == 11){
               moves(8, true);
               moves(0, false);
               moves(8, false);
            }
            else{
               while(arr[4][2][2] !=11){
                  moves(0, true);
               }
               moves(8, true);
               moves(0, true);
               moves(8, false);
            }
         }
         while(arr[2][2][2] != 13){
            if(obj[0][0] == 13 ||obj[0][3] == 13 || obj[0][6] ==13 ||obj[0][9] == 13){
               while(obj[0][9] != 13){
                  moves(0, true);
               }
               moves(5, false);
               moves(0, true);
               moves(5, true);
            } 
            else if(obj[0][2] == 13 ||obj[0][5] == 13 || obj[0][8] == 13 ||obj[0][11] == 13){
               while(obj[0][2] != 13){
                  moves(0, true);
               }
               moves(8, false);
               moves(0, false);
               moves(8, true);
            }
            else if(obj[2][5] == 13 || obj[2][6] == 13){
               moves(8, false);
               moves(0, true);
               moves(8, true);
            }
            else{
               while(arr[4][2][0] !=13){
                  moves(0, true);
               }
               moves(8, false);
               moves(0, false);
               moves(8, true);
            }
         }
         if(arr[2][0][0] == 6){
            arr[2][0][0] = 2;
         }     
         if(arr[2][0][2] == 8){
            arr[2][0][2] = 2;
         }    
         if(arr[2][2][0] == 11){
            arr[2][2][0] = 2;
         }    
         if(arr[2][2][2] == 13){
            arr[2][2][2] = 2;
         }
         autoUpdate();
      }
      public void correctCross(){
         while(!(arr[0][2][1] == 0)){
            moves(2, false);
         }
         while(!(arr[1][1][2] == 1 && arr[3][1][0] == 3 && arr[5][0][1] == 5)){
            if(!(arr[1][1][2] == 1)){
               int num = arr[1][1][2];
               if(arr[3][1][0] == 1){
                  moves(5, true);
                  moves(5, true);
                  moves(0, true);
                  moves(0, true);
                  moves(3, true);
                  moves(3, true);
               } 
               else if(arr[5][0][1] == 1){
                  moves(8, true);
                  moves(8, true);
                  moves(0, false);
                  moves(3, true);
                  moves(3, true);
               }
               if(num ==5){
                  moves(0, true);
                  moves(8, true);
                  moves(8, true);
                  if(arr[3][1][0] != 3){
                     moves(0, true);
                     moves(5, true);
                     moves(5, true);
                  }
               } 
               else if(num == 3){
                  moves(0, true);
                  moves(0, true);
                  moves(5, true);
                  moves(5, true);
                  if(arr[5][0][1] != 5){
                     moves(0, true);
                     moves(8, true);
                     moves(8, true);
                  }
               }
            }
            if(!(arr[5][0][1] == 5)){
               moves(8, true);
               moves(8, true);
               moves(0, false);
               moves(5, true);
               moves(5, true);
               moves(0, true);
               moves(8, true);
               moves(8, true);
            }
         }
      }
      public void cross(){
         int num = 0;
         while(!(arr[2][0][1] == 2 && arr[2][1][0] == 2 && arr[2][1][2] == 2 && arr[2][2][1] == 2)){
            num++;
            if(obj[6][4] == 2 || obj[6][7] == 2 || obj[6][10] == 2){
               if(arr[2][0][1] == 2){
                  moves(2, false);
               }
               else{
                  while(arr[2][0][1] != 2){
                     moves(6, true);
                  }
               }
            } 
            else if(obj[8][4] == 2 || obj[8][7] == 2 || obj[8][10] == 2){
               if(arr[2][2][1] == 2){
                  moves(2, false);
               }
               else{
                  while(arr[2][2][1] != 2){
                     moves(8, true);
                  }
               }
            } 
            else if(obj[3][4] == 2 || obj[3][7] == 2 || obj[3][10] == 2){
               if(arr[2][1][0] == 2){
                  moves(2, false);
               } 
               else{
                  while(arr[2][1][0] != 2){
                     moves(3, true);
                  }
               }
            } 
            else if(obj[5][4] == 2 || obj[5][7] == 2 || obj[5][10] == 2){
               if(arr[2][1][2] == 2){
                  moves(2, false);
               } 
               else{
                  while(arr[2][1][2] != 2){
                     moves(5, true);
                  }
               }
            }
            else if(obj[4][5] == 2||obj[4][3] == 2){
               if(arr[2][2][1] == 2){
                  moves(2, false);
               } 
               else{
                  moves(8, true);
               }
            } 
            else if(obj[4][9] == 2||obj[4][11] == 2){
               if(arr[2][0][1] == 2){
                  moves(2, false);
               } 
               else{
                  moves(6, true);
               }
            }
            else if(obj[7][5] == 2||obj[7][3] == 2){
               if(arr[2][1][2] == 2){
                  moves(2, false);
               } 
               else{
                  moves(5, true);
               }
            } 
            else if(obj[7][9] == 2||obj[7][11] == 2){
               if(arr[2][1][0] == 2){
                  moves(2, false);
               } 
               else{
                  moves(3, true);
               }
            }
         }
      }
      public void changeFace(int face1, int face2){
         change = false;
         undo[undocount][0] = face1;
         undo[undocount][1] = face2;
         move = false;
         white();
         System.out.println("Change face to " + face1 + " with " + face2 + " on top.");
         if(face1 == 0 || face1 == 5){
            cubeDown();
         }
         while(arr[2][1][1] != face1){
            cubeRight();
         }
         while(arr[0][1][1] != face2){
            cubeSide();
         }
         move = true;
         undocount++;
         change = true;
      }
      public void white(){
         System.out.println("Change face to white with blue on top.");
         if(arr[2][1][1] != 2){
            if(arr[0][1][1]==2 ||arr[5][1][1]==2){
               cubeDown();
            }
            while(arr[2][1][1] != 2){
               cubeRight();
            }
            change = true;
         }
         while(arr[0][1][1]!=0){
            cubeSide();
         }
      }   	
   	//rotate le face
      public void cubeRight(){
         moves(6, false);
         moves(7, false);
         moves(8, false);
      }
      public void cubeDown(){
         moves(3, false);
         moves(4, false);
         moves(5, false);
      }
      public void cubeSide(){
         moves(0, false);
         moves(1, false);
         moves(2, false);
      }
   	//0 = up/right;
   	//1 = down/left;
      public void moves(int index, boolean direction){
         if(move){
            if(index==0){
               if(direction){
                  System.out.println("Rotate Back face to the left");
               }
               else{
                  System.out.println("Rotate Back face to the right");
               }
            }
            else if(index == 2){
               if(direction){
                  System.out.println("Rotate Front face to the left");
               }
               else{
                  System.out.println("Rotate Front face to the right");
               }
            }
            else if(index<6){
               if(direction){
                  System.out.println("Rotate col " + (index - 2) + " up");
               }
               else{
                  System.out.println("Rotate col " + (index - 2) + " down");
               }
            }
            else if(index<9){
               if(direction){
                  System.out.println("Rotate row " + (index - 5) + " to the left");
               }
               else{
                  System.out.println("Rotate row " + (index - 5) + " to the right");
               }
            }
         }
         if(change){
            undo[undocount][0] = index;
            undo[undocount][1] = direction;
            undocount++;
            change = true;
         }
         if(!direction){ 
            int num1 = obj[index][11];
            int num2 = obj[index][10];
            int num3 = obj[index][9];
            for(int i = 11; i>2; i--){
               obj[index][i] = obj[index][i-3];
            } 
            obj[index][2] = num1;
            obj[index][1] = num2;
            obj[index][0] = num3;
         } 
         else{
            int num3 = obj[index][0];
            int num2 = obj[index][1];
            int num1 = obj[index][2];
            for(int i =0; i<9; i++){
               obj[index][i] = obj[index][i+3];
            } 
            obj[index][11] = num1;
            obj[index][10] = num2;
            obj[index][9] = num3;
         }
         if(index == 0){
            rotator(4, !direction);
         } 
         else if(index == 2){
            rotator(2, direction);
         } 
         else if(index == 3){
            rotator(1, !direction);
         } 
         else if(index == 5){
            rotator(3, direction);
         } 
         else if(index == 6){
            rotator(0, !direction);
         } 
         else if(index == 8){
            rotator(5, direction);
         }
         update(index);
      }
      public void rotator(int index, boolean direction){
         int temp[] = new int[9];
         int count = 0;
         if(index==1||index == 3){
            direction = !direction;
         }            
         for (int i =0; i<3; i++){
            for (int j = 0; j<3; j++){
               temp[count] = arr[index][i][j];
               count++;
            }
         }
         count = 0;
         if (!direction){
            for(int i =0; i<3; i++){
               for(int j = 0; j<3; j++){
                  arr[index][j][2-i] = temp[count];
                  count++;
               }
            }
         } 
         else{
            for(int i =0; i<3; i++){
               for(int j = 0; j<3; j++){
                  arr[index][2-j][i] = temp[count];
                  count++;
               }
            }
         }
      }
      public void update(int index){
         if(index<=2){
            for(int x = 0; x<3; x++){
               arr[0][0][x] = obj[0][x];
               arr[0][1][x] = obj[1][x];
               arr[0][2][x] = obj[2][x];
            }
            for(int x = 3; x<6; x++){
               arr[3][x-3][2] = obj[0][x];
               arr[3][x-3][1] = obj[1][x];
               arr[3][x-3][0] = obj[2][x];
            }
            for(int x = 6; x<9; x++){
               arr[5][2][2-(x-6)] = obj[0][x];
               arr[5][1][2-(x-6)] = obj[1][x];
               arr[5][0][2-(x-6)] = obj[2][x];
            }
            for(int x = 9; x<12; x++){
               arr[1][2-(x-9)][0]=obj[0][x];
               arr[1][2-(x-9)][1]=obj[1][x];
               arr[1][2-(x-9)][2]=obj[2][x];
            }
         }
         else if(index<=5){
            for(int x = 0; x<3; x++){
               arr[2][x][0] = obj[3][x];
               arr[2][x][1] = obj[4][x];
               arr[2][x][2] = obj[5][x];
            }
            for(int x = 3; x<6; x++){
               arr[5][x-3][0] = obj[3][x];
               arr[5][x-3][1] = obj[4][x];
               arr[5][x-3][2] = obj[5][x];
            }
            for(int x = 6; x<9; x++){
               arr[4][2-(x-6)][2] = obj[3][x];
               arr[4][2-(x-6)][1] = obj[4][x];
               arr[4][2-(x-6)][0] = obj[5][x];
            }
            for(int x = 9; x<12; x++){
               arr[0][x-9][0] = obj[3][x];
               arr[0][x-9][1] = obj[4][x];
               arr[0][x-9][2] = obj[5][x];
            }
         }
         else{
            for(int x = 0; x<3; x++){
               arr[2][0][x] = obj[6][x];
               arr[2][1][x] = obj[7][x];
               arr[2][2][x] = obj[8][x];
            }
            for(int x = 3; x<6; x++){
               arr[3][0][x-3] = obj[6][x];	
               arr[3][1][x-3] = obj[7][x];
               arr[3][2][x-3] = obj[8][x];
            }
            for(int x = 6; x<9; x++){
               arr[4][0][x-6] = obj[6][x];
               arr[4][1][x-6] = obj[7][x];
               arr[4][2][x-6] = obj[8][x];
            }
            for(int x = 9; x<12; x++){
               arr[1][0][x-9] = obj[6][x];
               arr[1][1][x-9] = obj[7][x];
               arr[1][2][x-9] = obj[8][x];
            }
         }
         autoUpdate();
      }
      public void autoUpdate(){
         for(int x = 0; x<3; x++){
            obj[0][x] = arr[0][0][x];
            obj[1][x] = arr[0][1][x];
            obj[2][x] = arr[0][2][x];
            obj[3][x] = arr[2][x][0];
            obj[4][x] = arr[2][x][1];
            obj[5][x] = arr[2][x][2];
            obj[6][x] = arr[2][0][x];
            obj[7][x] = arr[2][1][x];
            obj[8][x] = arr[2][2][x];
         }
         for(int x = 3; x<6; x++){
            obj[0][x] = arr[3][x-3][2];
            obj[1][x] = arr[3][x-3][1];
            obj[2][x] = arr[3][x-3][0];
            obj[3][x] = arr[5][x-3][0];
            obj[4][x] = arr[5][x-3][1];
            obj[5][x] = arr[5][x-3][2];
            obj[6][x] = arr[3][0][x-3];	
            obj[7][x] = arr[3][1][x-3];
            obj[8][x] = arr[3][2][x-3];
         }
         for(int x = 6; x<9; x++){
            obj[0][x] = arr[5][2][2-(x-6)];
            obj[1][x] = arr[5][1][2-(x-6)];
            obj[2][x] = arr[5][0][2-(x-6)];
            obj[3][x] = arr[4][2-(x-6)][2];
            obj[4][x] = arr[4][2-(x-6)][1];
            obj[5][x] = arr[4][2-(x-6)][0];
            obj[6][x] = arr[4][0][x-6];
            obj[7][x] = arr[4][1][x-6];
            obj[8][x] = arr[4][2][x-6];
         }
         for(int x = 9; x<12; x++){
            obj[0][x] = arr[1][2-(x-9)][0];
            obj[1][x] = arr[1][2-(x-9)][1];
            obj[2][x] = arr[1][2-(x-9)][2];
            obj[3][x] = arr[0][x-9][0];
            obj[4][x] = arr[0][x-9][1];
            obj[5][x] = arr[0][x-9][2];
            obj[6][x] = arr[1][0][x-9];
            obj[7][x] = arr[1][1][x-9];
            obj[8][x] = arr[1][2][x-9];
         }
      }
      public void adder(){
         int count = 0;
         for (int i = 0; i<3; i++){
            for(int j = 0; j<4; j++){
               JPanel txt = new JPanel();
               txt.setBorder(BorderFactory.createLineBorder(Color.black));
               if(i==1||(i==0&&j==1)||(i==2&&j==1)){
                  create(txt, count);
                  count++;
               }
               add(txt);
            }
         }
      }
      public void create(JPanel pane, int count){
         pane.setLayout(new GridLayout(3, 3));
         for (int i =0; i<3; i++){
            for(int j = 0; j<3; j++){
               JTextField txt = new JTextField();
               panArr[count][i][j] = txt;
               txt.setBorder(BorderFactory.createLineBorder(Color.black));
               txt.addKeyListener(new TeyListener(txt));
               pane.add(txt);
            }
         }
      }
      public void efficient(){
         int previous = -1;
         ArrayList<Object> temp1 = new ArrayList<Object>();
         ArrayList<Object> temp2 = new ArrayList<Object>();
         for(int i = 0; i<undocount; i++){
            temp1.add(undo[i][0]);
            temp2.add(undo[i][1]);
         }
         while(previous!=temp1.size()){
            previous = temp1.size();
            for(int i = 0; i<(temp1.size()-2); i++){
               if(temp1.get(i) == temp1.get(i+1)){
                  if(temp2.get(i) != temp2.get(i+1)){
                     temp2.remove(i+1);
                     temp2.remove(i);
                     temp1.remove(i+1);
                     temp1.remove(i);
                  }
               }
               if(temp1.get(i) == temp1.get(i+1)&&temp1.get(i+1) == temp1.get(i+2)){
                  if(temp2.get(i) == temp2.get(i+1)&&temp2.get(i+1) == temp2.get(i+2)){
                     temp2.remove(i+2);
                     temp2.remove(i+1);
                     temp1.remove(i+2);
                     temp1.remove(i+1);
                     temp2.set(i, !(boolean)temp2.get(i));
                  }
               }
            }
         }
         for(int x = 0; x<10000; x++){
            for(int y = 0; y<2; y++){
               undo[x][y] = 0;
            }
         }
			undocount = temp1.size();
			for(int i =0; i<undocount; i++){
				undo[i][0] = temp1.get(i);
				undo[i][1] = temp2.get(i);
			}
      }
      public void read(){
         int num = 0;
         String txt = "";
         try{
            for(int x = 0; x<6; x++){
               for (int i =0; i<3; i++){
                  for(int j = 0; j<3; j++){
                     txt = panArr[x][i][j].getText();
                     num = Integer.parseInt(txt);
                     arr[x][i][j] = num;
                  }
               }
            }
            autoUpdate();
         }
            catch (NumberFormatException e){
               error();
            }
      }
      public void error(){
         JOptionPane.showMessageDialog(RubixPanel.this, 
                           "Please Enter Valid Numbers");
      }
      public void undo(){
         String str = "\n";
         for(int i =0; i<undocount; i++){
            str += "direction" + undo[i][0] + undo[i][1] + "\n";
         }
         System.out.println(str);
      }
      public void print(){
         String str = "\n";
         for(int j = 0; j<3; j++){
            str += Arrays.toString(arr[0][j]) + "\n";
         }
         for(int i = 0; i <3; i++){
            str = str + Arrays.toString(arr[1][i]) + Arrays.toString(arr[2][i]) + Arrays.toString(arr[3][i]) + Arrays.toString(arr[4][i]) + "\n";
         }
         for(int j = 0; j<3; j++){
            str += Arrays.toString(arr[5][j]) + "\n";
         }
         System.out.println(str);
         System.out.println();
         for(int i = 0; i <3; i++){
            System.out.println(Arrays.toString(obj[i]));
         }
         System.out.println();
         for(int i = 0; i <3; i++){
            System.out.println(Arrays.toString(obj[i+3]));
         }
         System.out.println();
         for(int i = 0; i <3; i++){
            System.out.println(Arrays.toString(obj[i+6]));
         }			
      }
      public void setter(){
         for(int x = 0; x<6; x++){
            for(int y = 0; y<3; y++){
               for(int z = 0; z<3; z++){
                  panArr[x][y][z].setText("" + arr[x][y][z]);
                  if(arr[x][y][z] == 0){
                     panArr[x][y][z].setBackground(Color.blue);
                  }
                  else if(arr[x][y][z] == 1){
                     panArr[x][y][z].setBackground(Color.orange);
                  }
                  else if(arr[x][y][z] == 2|| arr[x][y][z] == 6|| arr[x][y][z] == 8|| arr[x][y][z] == 11 || arr[x][y][z] == 13){
                     panArr[x][y][z].setBackground(Color.white);
                  }
                  else if(arr[x][y][z] == 3){
                     panArr[x][y][z].setBackground(Color.red);
                  }
                  else if(arr[x][y][z] == 4|| arr[x][y][z] == 26|| arr[x][y][z] == 28|| arr[x][y][z] == 31 || arr[x][y][z] == 33){
                     panArr[x][y][z].setBackground(Color.yellow);
                  }
                  else if(arr[x][y][z] == 5){
                     panArr[x][y][z].setBackground(Color.green);
                  }
               }}}}
   
   
      private class SolveListener implements ActionListener{
         public void actionPerformed(ActionEvent e){
            move = true;;
            try{
               read();
               for (int x = 0; x<6; x++){
                  for(int y = 0; y<3; y++){
                     for(int z = 0; z<3; z++){
                        copy[x][y][z] = arr[x][y][z];
                     }
                  }
               }
               undocount = 0;
               print();
               cross();
               System.out.println("Cross Done");
               print();
               correctCross();
               System.out.println("Cross Corrected");
               print();
               corners();
               System.out.println("1st Layer Complete");
               secondLayer();
               System.out.println("2nd Layer Complete");
               print();
               lastCross();
               System.out.println("YoroSwag");
               correctLastCross();
               System.out.println("YORO");
               print();
               alignCorners();
               print();
               finale();
               print();
               for (int x = 0; x<6; x++){
                  for(int y = 0; y<3; y++){
                     for(int z = 0; z<3; z++){
                        arr[x][y][z] = copy[x][y][z];
                     }
                  }
               }
               autoUpdate();
               setter();
               //undo();
               print();
               next.addActionListener(new NextListener());
               total = undocount;
               move = true;
               //undo();
					efficient();
               System.out.println(undocount);
               undocount = 0;
            }
               catch(ArrayIndexOutOfBoundsException x){
                  System.out.println("swag");
               }
         }}
      private class NextListener implements ActionListener{
         public void actionPerformed(ActionEvent e){
            System.out.println(change);
            System.out.println(undo[undocount][0]);
            System.out.println(undo[undocount][1]);
            if(undocount<total){
               boolean condition = true;
               try{
                  boolean swag = (boolean)undo[undocount][1];
               }
                  catch(ClassCastException c){
                     condition = false;
                  }
               if(condition){
                  System.out.println("yoro");
                  moves((int)undo[undocount][0], (boolean)undo[undocount][1]);
                  setter();
               } 
               else{
                  changeFace((int)undo[undocount][0], (int)undo[undocount][1]);
               }
               setter();
            }
         }
      }
      private class TestListener implements ActionListener{
         public void actionPerformed(ActionEvent e){
            int count = 0;
            int data[] = /*{4, 0, 3, 0, 0, 4, 3, 0, 1,
                  		3, 1, 0, 1, 1, 1, 4, 1, 1,
                  		2, 2, 2, 2, 2, 2, 2, 2, 2,
                  		0, 3, 5, 3, 3, 5, 3, 3, 1,
                  		4, 4, 0, 4, 4, 4, 5, 0, 1,
                  		5, 5, 5, 5, 5, 5, 0, 3, 4};*/
               
               {3, 0, 1, 0, 0, 0, 0, 0, 0,
                     4, 1, 1, 1, 1, 1, 1, 4, 0,
                     2, 2, 2, 2, 2, 2, 1, 2, 2,
                     3, 3, 4, 3, 3, 5, 3, 5, 0,
                     5, 4, 5, 1, 4, 4, 4, 3, 5,
                     4, 5, 5, 5, 5, 3, 2, 4, 3};
            for(int x = 0; x<6; x++){
               for(int y = 0; y<3; y++){
                  for(int z = 0; z<3; z++){
                     panArr[x][y][z].setText("" + data[count]);
                     if(data[count] == 0){
                        panArr[x][y][z].setBackground(Color.blue);
                     }
                     else if(data[count] == 1){
                        panArr[x][y][z].setBackground(Color.orange);
                     }
                     else if(data[count] == 2){
                        panArr[x][y][z].setBackground(Color.white);
                     }
                     else if(data[count] == 3){
                        panArr[x][y][z].setBackground(Color.red);
                     }
                     else if(data[count] == 4){
                        panArr[x][y][z].setBackground(Color.yellow);
                     }
                     else if(data[count] == 5){
                        panArr[x][y][z].setBackground(Color.green);
                     }
                     count++;
                  }}}}}
      private class TeyListener implements KeyListener{
         private JTextField txt;
         public TeyListener(JTextField txt) {
            this.txt = txt;
         }
         @Override
            public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_0){
               txt.setBackground(Color.blue);
            } 
            else if(e.getKeyCode() == KeyEvent.VK_0){
               txt.setBackground(Color.blue);
            }
            else if(e.getKeyCode() == KeyEvent.VK_1){
               txt.setBackground(Color.orange);
            }
            else if(e.getKeyCode() == KeyEvent.VK_2){
               txt.setBackground(Color.white);
            }
            else if(e.getKeyCode() == KeyEvent.VK_3){
               txt.setBackground(Color.red);
            }
            else if(e.getKeyCode() == KeyEvent.VK_4){
               txt.setBackground(Color.yellow);
            }
            else if(e.getKeyCode() == KeyEvent.VK_5){
               txt.setBackground(Color.green);
            }
            
         }
         
         @Override
            public void keyTyped(KeyEvent e) {
            
         }
         
         @Override
            public void keyReleased(KeyEvent e) {
            
         }				
         			
      }}