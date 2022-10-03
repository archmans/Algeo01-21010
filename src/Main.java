import java.util.Scanner;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        int a, b, op;
        Scanner input = new Scanner(System.in);
        System.out.println("===========MENU===========");
        System.out.println("1. Sistem Persamaan Linear");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Interpolasi Bicubic");
        System.out.println("6. Regresi Linear Berganda");
        System.out.println("7. Keluar");
        System.out.println("");

        System.out.print("Pilih Menu:");
        op = input.nextInt();

        switch (op) {
            case 1:
                System.out.println("1. Metode Eliminasi Gauss");
                System.out.println("2. Metode Eliminasi Gauss-Jordan");
                System.out.println("3. Metode Matriks Balikan");
                System.out.println("4. Kaidah Cramer");
                int opp = input.nextInt();

                System.out.print("berapa baris : ");
                a = input.nextInt();
                System.out.print("berapa kolom : ");
                b = input.nextInt();
                double x[][] = new double[a][b];
                System.out.println("Masukkan matriks");
                x = inputMat(x, a, b);
                switch (opp){
                    case 1:
                        x = gauss(x);
                        displayMat(x);
                        double[] hasilGauss = new double[x.length];
                        hasilGauss = solveGauss(x);
                        for(int i=0;i<a;i++){
                            System.out.println("x"+(i+1)+" = "+hasilGauss[i]);
                        }
                        System.out.println("ketik apa saja untuk kembali ke menu");
                        String abc = input.next();
                        break;
                    case 2:
                        x = jordan(x);
                        displayMat(x);
                        double[] hasilJordan = new double[x.length];
                        hasilJordan = solveGaussJordan(x);
                        for(int i=0;i<a;i++){
                            System.out.println("x"+(i+1)+" = "+hasilJordan[i]);
                        }
                        System.out.println("ketik apa saja untuk kembali ke menu");
                        String abcd = input.next();
                        break;
                    case 3:
                        System.out.println("input b : ");
                        double[][] bb = new double[x.length][1];
                        for(int i=0;i<x.length;i++){
                            bb[i][0] = input.nextDouble();
                        }
                        double[][] hasil = new double[x.length][1];

                        hasil = solInv(x, bb);

                        for(int i=0;i<x.length;i++){
                            System.out.println("x"+(i+1)+" = "+ hasil[i][0]);
                        }
                        System.out.println("ketik apa saja untuk kembali ke menu");
                        String abcde = input.next();
                        break;
                    case 4: 
                        cramer(x);
                        System.out.println("ketik apa saja untuk kembali ke menu");
                        String abcdef = input.next();
                        break;
                }
                main(args);
                break;
            case 2:
                System.out.println("1. Input file");
                System.out.println("2. Input matriks");
                int metodeDet = input.nextInt();
                switch (metodeDet){
                    case 1:
                        try {
                            double d[][] = readFile();
                            System.out.println(determinan2(d));
                            break;
                        } catch (FileNotFoundException e) {
                            break;
                        }
                    case 2:
                    System.out.print("berapa baris : ");
                    a = input.nextInt();
                    System.out.print("berapa kolom : ");
                    b = input.nextInt();
                    double y[][] = new double[a][b];
                    double temp[][] = new double[a][b];
                    System.out.println("Masukkan matriks");
                    y = inputMat(y, a, b);
                    temp = copyMat(y, temp);
                    displayMat(y);
                   
                    double det = determinan2(temp);
                    System.out.println("determinan = " + det);
                    matrixToFileDet(y);
                }
                main(args);
                break;        
            case 3:
                System.out.println("1. Input file");
                System.out.println("2. Input matriks");
                int metodeInv = input.nextInt();
                switch (metodeInv){
                    case 1:
                        try {
                            double d[][] = readFile();
                            displayMat((invers(d)));
                            break;
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            break;
                        }
                    case 2:
                    System.out.print("berapa baris : ");
                    a = input.nextInt();
                    System.out.print("berapa kolom : ");
                    b = input.nextInt();
                    double z[][] = new double[a][b];
                    
                    System.out.println("Masukkan matriks");
                    z = inputMat(z, a, b);
                    displayMat(z);
                    System.out.println("");
                    displayMat(invers(z));
                    matrixToFileInv(invers(z));
                    break;
                }
                main(args);
                break;
            case 4:
                double c;            
                System.out.print("berapa titik :");
                a = input.nextInt();
                System.out.print("nilai x di berapa : ");
                c = input.nextDouble();
                
                double[][] in = new double[a][2];
                
                for(int i=0;i<in.length;i++){
                    for(int j=0;j<in[0].length;j++){
                        in[i][j] = input.nextDouble();
                    }
                }
                pol(in, a-1, c);
                main(args);
                break;
            case 5:
            case 6:
                System.out.println("REGRESI LINEAR BERGANDA");
                System.out.println("Berapa jumlah baris sampel?");
                int row = input.nextInt();
                System.out.println("Berapa kolom ( x dan y ) ?");
                int col = input.nextInt();
            
                double M[][] = new double[row][col];
                System.out.println("Masukkan input");
                M = inputMat(M, row, col);
            
                double inputx[] = new double[col-1];
                for(int i=0; i<inputx.length; i++)
                {   
                    System.out.println("input X");
                    inputx[i]=input.nextDouble();
                }
                regresi(M, inputx, row, col);
                main(args);
                break;
            case 7:
                System.out.println("Terimakasih sudah menggunakan jasa kami");
                System.out.println("Sampai bertemu lagii");
                break;
                
            default:
                System.out.println("Anda memilih menu yang salah");
                main(args);
        }
        System.out.println("");
        
        input.close();
    }

    public static double[][] inputMat(double[][] m, int a, int b) {
        Scanner input = new Scanner(System.in);
        m = new double[a][b];
        for(int i=0;i<a;i++){
            for(int j=0;j<b;j++){
                m[i][j] = input.nextDouble();
            }
        }
        // input.close();
        return m;
    }

    public static void displayMat(double[][]m) {
        int a,b;
        a = m.length;
        b = m[0].length;
        for(int i=0;i<a;i++){
            System.out.print("[");
            for(int j=0;j<b;j++){
                System.out.print(m[i][j]+" ");
            }
            System.out.println("]");
        }
    }

    public static double[][] copyMat(double[][]m, double[][]mn){
        for(int i=0;i<m.length;i++){
            for(int j=0;j<m[0].length;j++){
                mn[i][j] = m[i][j];
            }
        }
        return mn;
    }

    public static double[][] createIdentitas(int row, int col){
        double[][] iden = new double[row][col];
        if (isSquare(iden)){
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    if(i == j){
                        iden[i][j] = 1;
                    }else{
                        iden[i][j] = 0;
                    }
                }
            }
        }else{
            System.out.println("Tidak bisa jadi matriks identitas");
        }

        return iden;
    }

    public static double[][] gabungMat(double[][] m1, double[][] m2) {
        double[][] temp = new double[m1.length][m1[0].length+m2[0].length];
        copyMat(m1, temp);
        if(m1.length == m2.length){
            for(int i=0;i<m1.length;i++){
                for(int j=m1[0].length; j<m1[0].length+m2[0].length;j++){
                    temp[i][j] = m2[i][j-m2[0].length];
                }
            }
        }else{
            System.out.println("tidak bisa digabung");
        }
        return temp;
        
    }

    public static boolean isSquare(double[][] m){
        int row,col;
        row = m.length;
        col = m[0].length;

        return (row==col);
    }

    public static double getDiagonal(double[][] m, int i){
        return (m[i][i]);
    }

    public static void tukarBaris(double[][] m, int r1, int r2){
        double [] temp = m[r1];
        m[r1] = m[r2];
        m[r2] = temp;
    }

    public static boolean isRowZero(double[][] m, int r){
        int countZ =0;
        for(int j=0;j<m[0].length;j++){
            if(m[r][j]==0){
                countZ+=1;
            }
        }
        if (countZ==m[0].length){
            return true;
        }else{
            return false;
        }
    
    }
    public static double[][] multiplyMatrix(double[][] m1, double[][] m2){
        int i,j,k;
        double[][] mt = new double[m1.length][m2[0].length];
        for (i=0;i<m1.length;i++){
            for (j=0;j<m2[0].length;j++){
                mt[i][j] = 0;
            }    
        }
    
        for (i=0;i<m1.length;i++){
            for (j=0;j<m2[0].length;j++){
                for (k=0;k<m2.length;k++){
                    mt[i][j] += m1[i][k]*m2[k][j];
                }
            }
        }
        return mt;
    }

    //==================FUNGSI UTAMA MATRIX=========================//


    //DETERMINAN
    //metode segitiga bawah
    public static double determinan1(double[][] m) {
        int row,col;
        double count=1;
        row = m.length;
        col = m[0].length;

        for (int i=0;i<row;i++){
           for(int j=0;j<col;j++){
                 if(i<j){
                    double c = m[j][i]/m[i][i];
                    for(int k=0;k<col;k++){
                       m[j][k] = m[j][k]-(c*m[i][k]);
                    }
                 }
           }
        }

        for (int i=0;i<row;i++){
           count*= m[i][i];
        }
        
        return count;
      }

        // Metode perkalian baris kofaktor
      public static double determinan2(double[][] m){
        double det=0;
        int i,sign=1;
        if (m.length == 1 && m[0].length == 1){
            return(m[0][0]);
        }
        for (i=0;i<m[0].length;i++){
            det += m[0][i]*sign*determinan2(kofaktor(m,0,i));
            sign*=-1;
        }
        return det;
        // return hitung(m);
    }

    public static double[][] kofaktor(double[][] m, int row, int col){
        int i,j,ikof=0,jkof=0;
        double[][] kof = new double[m.length-1][m[0].length-1];
        for (i=0;i<row;i++) {
            for (j=0;j<col;j++) {
                if(i!=row && j!=col){
                    kof[ikof][jkof] = m[i][j];
                    if (jkof==kof.length){
                        ikof+=1;
                        jkof=0;
                    }else{
                        jkof+=1;
                    }
                }
            }
        }
        return kof;
    }
    // METODE CRAMER
    public static void cramer(double[][] m) {
        // defisini variable
        double y[];
        double mCopy[][];
        final double mCopyConst[][];
        y = new double[m.length];
        mCopy = new double[m.length][m[0].length-1];
        mCopyConst = new double[m.length][m[0].length-1];

        // Copy matrix asli kedalam matrix temporary
        for(int i=0;i<mCopy.length;i++){
            for(int j=0;j<mCopy[0].length;j++){
                mCopy[i][j] = m[i][j];
                mCopyConst[i][j] = m[i][j];
            }
        }

        double detA = determinan1(mCopy);
        System.out.println("determinan = " + detA);
        System.out.println("");

        // displayMat(mCopyConst);

        //memasukkan hasil dari spl ke sebuah array baru
        for(int i=0;i<m.length;i++){
            y[i] = m[i][m[0].length-1];
            // System.out.println(y[i]);
        }
        // System.out.println("");


        // Proses perhitungan kaidah Cramer
        for (int j=0;j<mCopy[0].length;j++){
            double tempD = 0;
            // memasukkan ulang matrix yang telah dirubah agar kembali seperti semula
            for(int w=0;w<mCopy.length;w++){
                for(int e=0;e<mCopy[0].length;e++){
                    mCopy[w][e] = mCopyConst[w][e];
                }
            }
            // memasukkan hasil spl ke setiap kolom
            for(int k=0;k<mCopy.length;k++){
                mCopy[k][j] = y[k];
            }
    
            //menghitung deterinan dari matriks yang sudah dimasukkkan hasil dari spl
            tempD = determinan1(mCopy);

            //membagi determinan setiap matriks yang diganti dengan determinan asli
            System.out.println("x" + (j+1) +"= "+tempD/detA);
        }
    }

    // ELIMINASI GAUSS
    public static double[][] gauss(double[][] m){
        int row,col;
        row = m.length;
        col = m[0].length;

        if(isSquare(m)){
            m = rowZero(m);
            for (int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    if(i<j){
                        double c = m[j][i]/m[i][i];
                        for(int k=0;k<col;k++){
                            m[j][k] = m[j][k]-(c*m[i][k]);
                        }
                    }
                }
            }
        }else{
            m = rowZero(m);
            int q = (col-row);
            for (int i=0;i<row-1;i++){
                for(int j=0;j<col-q;j++){
                    if(i<j){
                        double c = m[j][i]/m[i][i];
                        for(int k=0;k<col;k++){
                            m[j][k] = m[j][k]-(c*m[i][k]);
                        }
                    }
                }
            }
        }
        for (int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(m[i][i]!=1 || m[i][i]!=0){
                    double c = getDiagonal(m, i);
                    if(isRowZero(m, i)){
                        continue;
                    }else{
                        for(int k=j;k<col;k++){
                            m[i][k] = m[i][k]/c;
                        }
                    }
                }
            }
        }


        return m;
    }
    // GAUSS JORDAN
    public static double[][] jordan(double[][] m) 
    {
        m = gauss(m);
        int row,col,i,j,k;
        row = m.length;
        col = m[0].length;

        if(col<=row){
            for(i=row-1; i>=1; i--){
                for(j=i-1; j>=0; j--){
                    double c = m[j][i];
                    for(k=row-1; k>=0; k--){
                        m[j][k] = m[j][k]-(m[i][k]*c);
                    }
                }
            }
        }else{
            int pivotCol = 0;
            for (k = 0; k < row; k++) {
                if (pivotCol == col-1){
                    break;
                }
                while (isAllZero(m, pivotCol) && (pivotCol < col)){
                    pivotCol++;
                }
                double pivot = m[k][pivotCol];
                if (isZero(pivot)) {
                    for (i = k+1; i<row; i++) {
                        if (!isZero(m[i][pivotCol])) {
                            for (j=0; j<col; j++) {
                                double temp = m[k][j];
                                m[k][j] = m[i][j];
                                m[i][j] = temp;
                            }
                            break;
                        }
                    }
                }
                pivot = m[k][pivotCol];
                if (isZero(pivot)) {
                    continue;
                } else {
                    for (j = pivotCol; j<col; j++) {
                        m[k][j] = m[k][j] / pivot;
                    }
                    for (i = 0; i<row; i++) {
                        if ((i == k) || m[i][pivotCol] == 0) {
                            continue;
                        }
                        double faktor = m[i][pivotCol];
                        for (j = pivotCol; j < col; j++) {
                            m[i][j] = (m[i][j] - (faktor * m[k][j]));
                        }
                    }
                }
                pivotCol++;
            }
        }
        return m;
    }
    protected static boolean isZero(double x) {
        double epsilon = 1.0e-12;
        return ((x < epsilon) && (x > -epsilon));
    }
    public static boolean isAllZero(double[][] matrix, int pivotCol){
        boolean allZero = true;
        for (int i = 0; i < matrix.length; i++){
            if (!isZero(matrix[i][pivotCol])){
                allZero = false;
            }
        }
        return  allZero;
    }
      

    public static double[][] rowZero(double[][] m){
        int row = m.length;
        int barisAkhir = m.length;
        for(int i=0;i<row-1;i++){
            if(isRowZero(m, i)){
                tukarBaris(m, i, barisAkhir-1);
                barisAkhir-=1;
            }
        }
        return m;
    }

    //INVERS
    public static double[][] invers(double[][] m) {
        int row, col;
        row = m.length;
        col = m[0].length;
        double[][] gbIden = new double[row][2 * col];
        double y[][] = new double[row][col];
        double z[][] = new double[row][col];
        y = createIdentitas(row, col);
    
        for (int i = 0; i < row; i++) {
          for (int j = 0; j < col * 2; j++) {
            if (j >= col) {
              gbIden[i][j] = y[i][j - col];
            } else {
              gbIden[i][j] = m[i][j];
            }
          }
        }
        row = gbIden.length;
        col = gbIden[0].length;
        for (int i = 0; i < row; i++) {
          for (int j = 0; j < col; j++) {
            System.out.print(gbIden[i][j] + " ");
          }
          System.out.println("");
        }
        System.out.println("");
    
        gbIden = jordan(gbIden);
    
        for (int i = 0; i < row; i++) {
          for (int j = 0; j < gbIden[0].length; j++) {
            if (j >= gbIden[0].length / 2) {
              z[i][j - z.length] = gbIden[i][j];
            }
    
          }
        }
        return z;
      }
    
    private static double[][] readFile() throws FileNotFoundException {
        int columns = 0;
        int rows = 0;
        Scanner scanner = new Scanner(chooseTextFile());
        while(scanner.hasNextLine()){
            if(rows == 0){
                columns = (scanner.nextLine().trim().split("," + " ")).length;
                }
            else scanner.nextLine();
                    rows = rows + 1;
                }
            double[][] numArray = new double[rows][columns];
            Scanner sc = new Scanner(chooseTextFile());
            while (sc.hasNextLine()) {
                for (int i = 0; i < numArray.length; i++) {
                    String[] line = sc.nextLine().trim().split("," + " ");
                    for (int j = 0; j < line.length; j++) {
                        numArray[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }
            return numArray;
        }
    
        private static File chooseTextFile() {
            FileDialog dialog = new FileDialog((Frame) null, "Pilih file");
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            File[] file = dialog.getFiles();
            return file[0];
        }
        public static double[] solveGaussJordan(double[][] m) {
            double[] n = new double[m.length];
            for (int i = 0; i < m.length; i++) {
              n[i] = m[oneInRow(m, i)][m[0].length - 1];
            }
            return n;
        }
        public static double[][] solInv(double[][] m, double[][] b){
            double[][] matInv = new double[m.length][m[0].length];
            double[][] hasil = new double[m.length][m[0].length];
    
            matInv = invers(m);
            displayMat(matInv);
            hasil = multiplyMatrix(matInv, b);
            return hasil;
        }

        public static int oneInRow(double[][]m, int i){
            int found = 0;
            for(int j=0;j<m[0].length;j++){
                if(m[i][j]==1){
                    found = j;
                    break;
                }
            }
            return found;
        }

        public static double[] solveGauss(double[][] m) {
            // Menyelesaikan SPL kasus 1 (rows = cols -1)
    
            int row = m.length;
            int col = m[0].length;
            double[] hasil = new double[col - 1];
            for (int i = row - 1; i >= 0; i--) {
                hasil[i] = m[i][col - 1];
                for (int j = (col - 2); j > i; j--) {
                    hasil[i] -= m[i][j] * hasil[j];
                }
    
            }
    
            return hasil;
        }

        public static void pol(double[][] n, int x, double y) {
            double[][] m = new double[n.length][x + 2];
            double[][] o = new double[n.length][x + 2];
            double[] jawaban = new double[n.length];
            double[] jaw = new double[n.length];
            double hasil = 0;
        
            int row = n.length;
            for (int i = 0; i < row; i++) {
              for (int j = 0; j <= x + 1; j++) {
                if (j == x + 1) {
                  m[i][j] = n[i][1];
                } else {
                  m[i][j] = (Math.pow(n[i][0], j));
                }
              }
            }
        
            displayMat(m);
            o = jordan(m);
            jawaban = solveGaussJordan(o);
        
            for (int i = 0; i < row; i++) {
              System.out.println("x" + (i + 1) + " = " + jawaban[i]);
            }
        
            for (int i = 0; i < jawaban.length; i++) {
              jaw[i] = Math.pow(y, i);
            }
            System.out.println("");
            for (int i = 0; i < jawaban.length; i++) {
              hasil += jawaban[i] * jaw[i];
            }
        
            System.out.print("f(x) = ");
            for (int i = (jawaban.length - 1); i >= 0; i--) {
              if (i == 0) {
                System.out.printf("%.5f", jawaban[i]);
              } else {
                System.out.printf("%.5fx^%d + ", jawaban[i], i);
              }
            }
            System.out.printf("\nf(%.2f) = %.5f", y, hasil);
          }

        //REGRESI LINEAR BERGANDA
        public static void regresi(double[][] M, double[] inputx, int row, int col)
        {
        int i, j, l;
        displayMat(M);
        double temp[][] = new double[col][col+1];
        System.out.println("\n");
    
        //baris pertama
        temp[0][0] = row;
            for(j=1; j<=col; j++)
            {
                for(l=0; l<row; l++)
                {
                    temp[0][j] += M[l][j-1];
                }
            }
    
        //kolom pertama
        for(i=1; i<=col-1; i++)
        {
            for(l=0; l<row; l++)
            {
                temp[i][0] += M[l][i-1];
            }
        }
    
        //sisa
        for(i=1; i<=col-1; i++)
        {
            for(j=1; j<=col; j++)
            {
                for(l=0; l<row; l++)
                {
                    temp[i][j] += M[l][i-1]*M[l][j-1];
                }
            }
        }
        displayMat(temp);
        System.out.println("\n");
        // jordan matrix temp untuk mendapatkan x0, x1, x3...
        temp = jordan(temp);
        displayMat(temp);
    
        double reg[] = new double[temp.length];
        reg = solveGaussJordan(temp);
        for (i=1; i<reg.length; i++)
        {
            reg[i] *= inputx[i-1];
        }
        
        // Hasil x0, x1, x3...
        for(i=0; i<reg.length; i++)
        {
            System.out.println(reg[i]);
        }
    
        // Total hasil regresi yang sudah dikali input x
        double sum = 0;
        for (i=0; i<reg.length; i++)
        {
            sum += reg[i];
        }
        System.out.println("Hasil regresi");
        System.out.println(sum);
        }
    public static void matrixToFileDet (double[][] m) throws FileNotFoundException, UnsupportedEncodingException{
            System.out.println("Masukkan nama file: ");
            Scanner input = new Scanner(System.in);
            String namaFile = input.next();
            PrintWriter writer = new PrintWriter(namaFile +".txt", "UTF-8");
            for(int i=0;i<m.length;i++){
                for(int j=0;j<m[0].length;j++){
                    if(j==m[0].length-1){
                        writer.print(m[i][j]);
                    }else{
                        writer.print(m[i][j]+ "," + " ");
                    }
                }
                writer.println("");
            }
            double det = determinan1(m);
            writer.println(det);
            
            writer.close();
        }
    public static void matrixToFileInv (double[][] m) throws FileNotFoundException, UnsupportedEncodingException{
        System.out.println("Masukkan nama file: ");
        Scanner input = new Scanner(System.in);
        String namaFile = input.next();
        PrintWriter writer = new PrintWriter(namaFile +".txt", "UTF-8");
        for(int i=0;i<m.length;i++){
            for(int j=0;j<m[0].length;j++){
                if(j==m[0].length-1){
                    writer.print(m[i][j]);
                }else{
                    writer.print(m[i][j]+ "," + " ");
                }
            }
            writer.println("");
        }        
        writer.close();
        }
}