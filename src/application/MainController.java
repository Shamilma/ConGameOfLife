package application;

import java.util.ArrayList;
import java.util.List;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;


/*
 * Controllerklasse som styrer kommunikasjon med bruker og GUI
 */

public class MainController implements Initializable {
	public final int hei = 100;
	public final int wid = 100;
	
	public boolean[][] board = new boolean[hei][wid];
	// Intern modell-klasse (deklarert her for enkelhetens skyld)
	// Burde være deklarert som en modell (f.eks. i model pakke ved bruk
	// av MVC
	private static class Point {
		public double x,y;
		public void draw(GraphicsContext gc, Color drawColor, double size) {
			gc.setFill(drawColor);
			gc.fillArc(x, y, size, size, 0, 360, ArcType.ROUND);
			
		}
		//.
	}
	
	// interne objekter relatert til GUI
	@FXML private MenuBar menuBar;
	@FXML private Canvas graphics;
	@FXML private ColorPicker colorPicker;
	@FXML private Slider sizeSlider;
	private List<Point> plist;
	
	// denne metoden blir kalt automatisk i oppstarten av programmet
	// kan assosieres med konstruktør
	public void initialize(java.net.URL location,
            java.util.ResourceBundle resources) {
		colorPicker.setValue(Color.BLACK);
		sizeSlider.setValue(5.0);
		plist = new ArrayList<Point>();
		draw();
	}
	
	// hjelpemetode som tegner grafikk til 'canvas' området i GUI
	public void draw() {
		GraphicsContext gc = graphics.getGraphicsContext2D();
		gc.clearRect(0, 0, graphics.widthProperty().doubleValue(), graphics.heightProperty().doubleValue());
		for (double i=0; i<hei; i++){
		double y = i * graphics.getHeight()/hei;
		gc.strokeLine(0, y, graphics.getWidth(), y);
		}
		for (double j=0;j<wid;j++){
			double x = j * graphics.getWidth()/wid;
			gc.strokeLine(x, 0, x, graphics.getHeight());
		}
		for ( Point p : plist ) {
			p.draw(gc, colorPicker.getValue(), sizeSlider.getValue());
		}
	}
	
	/*
	 * Følgende metoder håndterer actions fra bruker (via GUI)
	 */
	
	public void mouseDragged(MouseEvent event) {
		Point p = new Point();
		p.x = event.getX();
		p.y = event.getY();
		plist.add(p);
		draw();
	}
	
	public void exitEvent(ActionEvent event) {
		System.exit(0);
	}
	
	public void clearEvent(ActionEvent event) {
		plist.clear();
		draw();
	}
	
	public void newColorEvent(ActionEvent event) {
		draw();
	}
	
	public void newSizeEvent(MouseEvent e) {
		draw();
	}
}
