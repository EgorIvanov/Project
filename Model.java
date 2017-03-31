
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javax.swing.*;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.SceneAntialiasing;
import javafx.scene.PerspectiveCamera;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Timer;

public class Model extends JFrame {

    JFXPanel Panel = new JFXPanel();
    static float Grad = (float) (2 * Math.PI) / 360;
    static float Tr_Nea = (float) 0.1;
    static float Tr_Far = (float) 10000;
    static float alpha;
    static int leftX = 0;
    static int leftY = 0;
    static int rightX = 640;
    static int rightY = 489;
    static int elem_am = 0;

    static TPL[] elem_param = new TPL[100];

    public void elem_add(TPO iA, TPO iB, TPO iC, TPO iD, Color iCol) {
        elem_param[elem_am] = new TPL(iA, iB, iC, iD, iCol);
        elem_am = elem_am + 1;
    }

    public void param_clear() {
        elem_am = 0;
    }

    public void Turn(TPO iPo, float alph) { // Поворачивает точку iPo на угол aplha вокруг оси Y
        float X;
        float Z;
        float U = Grad * alph;
        X = (float) (iPo.X * Math.cos(U) - iPo.Z * Math.sin(U));
        Z = (float) (iPo.X * Math.sin(U) + iPo.Z * Math.cos(U));
        iPo.X = X;
        iPo.Z = Z;
    }


    public MeshView Triangles(TPO iA, TPO iB, TPO iC, TPO iD, Color iCol) {
        TriangleMesh Triangle1 = new TriangleMesh();
        Triangle1.getTexCoords().addAll(0, 0);
        Triangle1.getPoints().addAll(iA.X, iA.Y, iA.Z, iB.X, iB.Y, iB.Z, iC.X, iC.Y, iC.Z, iD.X, iD.Y, iD.Z);
        Triangle1.getTexCoords().addAll(0, 0, 0, 1, 1, 1, 1, 0);

        Triangle1.getFaces().addAll(0, 0, 1, 0, 2, 0, 2, 0, 3, 0, 0, 0, 0, 0, 3, 0, 2, 0, 2, 0, 1, 0, 0, 0);

        MeshView View = new MeshView(Triangle1);
        javafx.scene.paint.PhongMaterial mat = new javafx.scene.paint.PhongMaterial();
        mat.setDiffuseColor(iCol);
        View.setMaterial(mat);

        return View;
    }

    public Scene DrawFrame() {
        Group root = new Group();
        Scene frame = new Scene(root, 500, 500, true, SceneAntialiasing.BALANCED);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(Tr_Nea);
        camera.setFarClip(Tr_Far);
        camera.setTranslateZ(0);
        frame.setCamera(camera);

        root.getChildren().clear();
        return (frame);
    }


    public void initFrame(JFXPanel Panel) {
        Panel.setLocation(leftX, leftY);
        Panel.setScene(DrawFrame());
    }

    public void Tr_Init(int ileftX, int ileftY, int irightX, int irightY) {

        leftX = ileftX;
        leftY = ileftY;
        rightX = irightX;
        rightY = irightY;
    }

    public void cycle()  {
        param_clear();
        TPO A = new TPO(-100, -100, 0);
        Turn(A, alpha);
        A.Z = A.Z + 1000;
        TPO B = new TPO(-100, 100, 0);
        Turn(B, alpha);
        B.Z = B.Z + 1000;
        TPO C = new TPO(100, 100, 0);
        Turn(C, alpha);
        C.Z = C.Z + 1000;
        TPO D = new TPO(100, -100, 0);
        Turn(D, alpha);
        D.Z = D.Z + 1000;
        elem_add (A, B, C, D, Color.RED);
        Scene scene = DrawFrame();
        Panel.setScene(scene);
        alpha = alpha + 3;
    }

    public void Timer_proc(int s) {
            Timer timer = new Timer();
            timer.schedule(cycle(), s, s);
    }


    private void initComponents() {

        JButton button = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        button.setText("кнопка");
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        Timer_proc(100);
    }

    public static void main(String args[]) {
        new Model().setVisible(true);
    }
}