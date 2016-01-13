package com.nkwh.mathilde.ui.designcanvas;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public final class CanvasProjectDesign extends Canvas 
{
	private double _prevX = 0;
	private double _prevY = 0;
	private double _difX  = 0;
	private double _difY  = 0;
	
	public CanvasProjectDesign()
	{		
		widthProperty().addListener(evt -> drawShapes());
        heightProperty().addListener(evt -> drawShapes());      
        
        this.setOnMousePressed(new EventHandler<MouseEvent>() 
        {
			@Override
			public void handle(MouseEvent event) 
			{
				_prevX = event.getX();
				_prevY = event.getY();
			}
		});
        
        this.setOnMouseDragged(new EventHandler<MouseEvent>() 
        {
			@Override
			public void handle(MouseEvent event) 
			{
				_difX += event.getX() - _prevX;
				_difY += event.getY() - _prevY;
				_prevX = event.getX();
				_prevY = event.getY();
				drawShapes();
			}
		});
	}
	
	public void drawShapes() 
	{
		double width = getWidth();
        double height = getHeight();
        
		GraphicsContext gc = this.getGraphicsContext2D();
		
        gc.clearRect(0, 0, width, height);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1.2d);
        gc.strokeLine(_difX + 40, _difY + 10, _difX + 20, _difY + 200);
        gc.fillOval(_difX + 10, _difY + 60, 300, 300);
        gc.strokeOval(_difX + 60, _difY + 60, 30, 30);
//        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
//        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
//        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
//        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
//        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
//        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
//        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
//        gc.fillPolygon(new double[]{10, 40, 10, 40},
//                       new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolygon(new double[]{60, 90, 60, 90},
//                         new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolyline(new double[]{110, 140, 110, 140},
//                          new double[]{210, 210, 240, 240}, 4);
    }
	
	@Override
	public boolean isResizable() 
	{
		return true;
	}
	
	@Override
    public double prefWidth(double width) 
	{
        return getWidth();
    }

    @Override
    public double prefHeight(double height) 
    {
        return getHeight();
    }
}
