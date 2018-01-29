package view;

import java.awt.Graphics;

import javax.swing.JComponent;

import model.Model;

/**
 * Customer JComponent which overrides paintComponent method to
 * hand-render a polygon with the number of sides and color
 * determined by the Model provided.
 */
public class PolygonViewer
	extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	private static final int BORDER = 10;
	
	private Model model;
	
	public PolygonViewer( final Model model )
	{
		this.model = model;
	}
	
	@Override
	protected void paintComponent( Graphics g )
	{
		int width = (int)g.getClipBounds().getWidth(),
				height = (int)g.getClipBounds().getHeight();
//		g.drawArc( 10, 10, width - 20, height - 20,
//				0, 360 );
		
		g.setColor( model.getColor() );
		final int centerX = width / 2,
				centerY = height / 2,
				radiusX = (width / 2) - BORDER,
				radiusY = (height / 2) - BORDER,
				startRotation = 0;
		int x = (int)(Math.sin( startRotation ) * radiusX) + centerX,
				y = (int)(-Math.cos( startRotation ) * radiusY) + centerY,
				sides = model.getNumberSides();
		for( int i = 1; i <= sides; i++ ) {
			int newX = (int)(Math.sin( 2 * Math.PI * i / sides + startRotation )
					* radiusX) + centerX,
					newY = (int)(-Math.cos( 2 * Math.PI * i / sides + startRotation )
					* radiusY) + centerY;
			g.drawLine( x, y, newX, newY );
			x = newX;
			y = newY;
		}
	}
}