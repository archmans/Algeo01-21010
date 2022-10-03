import java.util.Scanner;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        int a, b, op, row, col;
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

        System.out.println("Pilih :");
        op = input.nextInt();

        switch (op) {
            case 1:
                System.out.println("1. Metode Eliminasi Gauss");
                System.out.println("2. Metode Eliminasi Gauss-Jordan");
                System.out.println("3. Metode Matriks Balikan");
                System.out.println("4. Kaidah Cramer");
                int opp = input.nextInt();

                System.out.println("berapa baris?");
                a = input.nextInt();
                System.out.println("berapa kolom?");
                b = input.nextInt();
                double x[][] = new double[a][b];
                System.out.println("Masukkan matriks");
                x = inputMat(x, a, b);
                switch (opp){
                    case 1:
                        x = gauss(x);
                        displayMat(x);
                        break;
                    case 2:
                        x = jordan(x);
                        displayMat(x);
                        break;
                    case 4: 
                        cramer(x);
                        break;
                }
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
                            // TODO Auto-generated catch block
                            // e.printStackTrace();
                            break;
                        }
                    case 2:
                    System.out.println("berapa baris?");
                    a = input.nextInt();
                    System.out.println("berapa kolom?");
                    b = input.nextInt();
                    double y[][] = new double[a][b];
                    double temp[][] = new double[a][b];
                    System.out.println("Masukkan matriks");
                    y = inputMat(y, a, b);
                    temp = copyMat(y, temp);
                    displayMat(y);
                    // displayMat(temp);
                    double det = determinan2(temp);
                    System.out.println("determinan = " + det);
                    matrixToFileDet(y);
                    }
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
                    System.out.println("berapa baris?");
                    a = input.nextInt();
                    System.out.println("berapa kolom?");
                    b = input.nextInt();
                    double z[][] = new double[a][b];
                    // double y[][] = new double[a][b];
                    
                    System.out.println("Masukkan matriks");
                    z = inputMat(z, a, b);
                    displayMat(z);
                    System.out.println("");
                    displayMat(invers(z));
                    matrixToFileInv(invers(z));
                    break;
                }
            break;
            case 4:
                double c;            
                System.out.println("berapa titik?");
                a = input.nextInt();
                // System.out.println("polinom berapa??");
                // b = input.nextInt();
                System.out.println("nilai x di berapa???");
                c = input.nextDouble();
                
                double[][] in = new double[a][2];
                // double[][] x= new double[a][b+1];
                // double[] jawaban = new double[a];
                
                
                for(int i=0;i<in.length;i++){
                    for(int j=0;j<in[0].length;j++){
                        in[i][j] = input.nextDouble();
                    }
                }
                pol(in, a-1, c);
                break;
            case 5:
            case 6:
                System.out.println("REGRESI LINEAR BERGANDA");
                System.out.println("berapa jumlah baris sampel?");
                row = input.nextInt();
                System.out.println("berapa kolom ( x dan y ) ?");
                col = input.nextInt();
            
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
                break;
            case 7:
                
                    
            default:
                System.out.println("yaiayy");
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

    public static double[][] addMatrix(double[][] m1, double[][] m2) {
        double[][] temp = new double[m1.length][m1[0].length];
        copyMat(m1, temp);
        if(m1.length == m2.length && m1[0].length == m2[0].length){
            for(int i=0;i<m1.length;i++){
                for(int j=0; j<m1[0].length;j++){
                    temp[i][j] = m1[i][j] + m2[i][j];
                }
            }
        }else{
            System.out.println("tidak bisa ditambah");
        }
        return temp;
    }

    public static double[][] substractMatrix(double[][] m1, double[][] m2) {
        double[][] temp = new double[m1.length][m1[0].length];
        copyMat(m1, temp);
        if(m1.length == m2.length && m1[0].length == m2[0].length){
            for(int i=0;i<m1.length;i++){
                for(int j=0; j<m1[0].length;j++){
                    temp[i][j] = m1[i][j] - m2[i][j];
                }
            }
        }else{
            System.out.println("tidak bisa ditambah");
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









    //==================FUNGSI UTAMA MATRIX=========================//


    //DETERMINAN
    public static double determinan(double[][] m)
    {
       double ans = 1;
       // matriks penyebut
       double penyebut[][] = new double[m.length][m[0].length];
       for ( int i = 0; i < m.length; i++)
       {
          for ( int j = 0; j < m[0].length; j++)
          {
             penyebut[i][j] = 1;
          }
       }
    
       // OBE matriks segitiga
       for ( int i = 0; i < m.length; i++)
       {
          if (m[i][i] == 0)
          {
             int j = i+1;
             while ( j < m.length)
             {
                if (m[j][i] != 0)
                {
                   int temp = (int)m[j][i];
                   m[j][i] = m[i][i];
                   m[i][i] = temp;
    
                   j++;
                   break;
                }
             }
    
             if ( j >= m.length)
             {
                return 0;
             }
             ans *= -1;
          }
          // pengurangan i,i hingga bernilai 0
          for ( int j = i+1; j < m.length; j++)
          {
             int k_pemb = (int)m[j][i];
             int k_peny = (int)m[i][i];
             m[j][i] = 0;
             penyebut[j][i] = 1;
    
             for (int k = i+1; k < m[0].length; k++)
             {
                int dec_pemb = k_pemb * (int)m[i][k];
                int dec_peny = k_peny * (int)penyebut[i][k];
    
                int x = (int)penyebut[j][k], y = dec_peny;
    
                int kpk = x*y;
    
                m[j][k] = m[j][k]*(kpk/x) - dec_pemb*(kpk/y);;
    
                penyebut[j][k] = kpk;
             }
             
          }
    
          ans *= (m[i][i]*1.0)/(penyebut[i][i]*1.0);
       }
       return ans;
    }

    public static double determinan2(double[][] m) {
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

        double detA = determinan(mCopy);
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
            // System.out.println("Berubah");
            // displayMat(mCopy);


            // System.out.println(det(mCopy));
            //menghitung deterinan dari matriks yang sudah dimasukkkan hasil dari spl
            tempD = determinan(mCopy);
            // System.out.println(tempD);
            // System.out.println(detA);
            //membagi determinan setiap matriks yang diganti dengan determinan asli
            System.out.println("x" + (j+1) +"= "+tempD/detA);
            // System.out.println("");

            // System.out.println("Balik lagi");
            // displayMat(mCopy);
            // System.out.println("");
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
    public static double[][] jordan(double[][] m) {
        m = gauss(m);
        int row,col;
        row = m.length;
        col = m[0].length;
        if(col<=row){
            for(int i=row-1;i>=1;i--){
                for(int j=i-1;j>=0;j--){
                    double c = m[j][i];
                    for(int k=row-1;k>=0;k--){
                        m[j][k] = m[j][k]-(m[i][k]*c);
                    }
                }
            }
        }else{
            int pivotCol = 0;
            for (int k = 0; k < row; k++) {
                //jika sudah sampai kolom terakhir maka hentikan proses
                if (pivotCol == col-1){
                    break;
                }
                //cek apakah kolom bernilai 0
                //jika 0 maka pivot bergeser ke kolom sebelah kanan-nya
                while (isAllZero(m, pivotCol) && (pivotCol < col)){
                    pivotCol++;
                }
                double pivot = m[k][pivotCol];
                /* cek apakah pivot = 0, jika 0 maka swap dengan yang tidak 0 */
                if (isZero(pivot)) {
                    for (int i = k + 1; i < row; i++) {
                        if (!isZero(m[i][pivotCol])) {
                            for (int j = 0; j < col; j++) {
                                double temp = m[k][j];
                                m[k][j] = m[i][j];
                                m[i][j] = temp;
                            }
                            break;
                        }
                    }
                }
                // melakukan pembagian pada baris pivot
                pivot = m[k][pivotCol];
                if (isZero(pivot)) {
                    continue;
                } else {
                    for (int j = pivotCol; j < col; j++) {
                        m[k][j] = m[k][j] / pivot;
                    }

                    // melakukan eliminasi pada baris bawah dan atasnya agar bernilai = 0
                    for (int i = 0; i < row; i++) {
                        if ((i == k) || m[i][pivotCol] == 0) {
                            continue;
                        }
                        double factor = m[i][pivotCol];
                        // System.out.println("ini faktornya "+factor);
                        for (int j = pivotCol; j < col; j++) {
                            m[i][j] = (m[i][j] - (factor * m[k][j]));
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
        // Mengembalikan true jika suatu kolom semua bernilai 0 elemennya

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
        // double[][] temp = new double[row][2*col];
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
    
        // temp = gbIden;
        for (int i = 0; i < row; i++) {
          for (int j = 0; j < gbIden[0].length; j++) {
            // System.out.println(j);
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
            // System.out.println(Arrays.deepToString(numArray));
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
        public static void pol(double[][] n, int x, double y) {
            // double[][] x = new double[4][j+1];
            // double[][] n = {{8,2.0794},{9,2.1972},{9.5,2.2513}};
            double[][] m = new double[n.length][x + 2];
            double[][] o = new double[n.length][x + 2];
            double[] jawaban = new double[n.length];
            double[] jaw = new double[n.length];
            double hasil = 0;
        
            int row = n.length;
            // int col = n[0].length;
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
              // System.out.println(jaw[i]);
            }
            System.out.println("");
            for (int i = 0; i < jawaban.length; i++) {
              hasil += jawaban[i] * jaw[i];
              // System.out.println(hasil);
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


            
        
            // return m;
        
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
            double det = determinan(m);
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
        // double[][] inv = invers(m);
        // writer.println(inv);
        
        writer.close();
}
}