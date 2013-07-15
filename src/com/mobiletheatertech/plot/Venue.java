package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;

/**
 * The space in which theater is to be done.
 * <p/>
 * All aspects of the lighting plot which are descriptions of the venue are encapsulated here.
 * <p/>
 * XML tag is 'venue'. Exactly one venue must be defined. Children may be any number of 'hangpoint'
 * tags. Required attributes are 'name', 'width', 'depth', and 'height'. (I expect to move the
 * 'name' attribute into its own tag at some point.)
 *
 * @author dhs
 * @since 0.0.2
 */
public class Venue extends Minder {

    private static Venue StaticVenue = null;
    private String name = null;
    private Integer width = null;
    private Integer depth = null;
    private Integer height = null;

    /**
     * Extract the venue description element from a list of XML nodes.
     *
     * @param list List of XML nodes
     * @throws AttributeMissingException If a required attribute is missing.
     */
    public static void ParseXML( NodeList list )
            throws AttributeMissingException
    {

        Node node = list.item( 0 );
        if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            new Venue( element );
        }
    }

    /**
     * Construct a {@code Venue} from an XML Element.
     *
     * @param element DOM Element defining a venue.
     * @throws AttributeMissingException if any attribute is missing.
     */
    public Venue( Element element ) throws AttributeMissingException {
        name = element.getAttribute( "name" );
        if (name.isEmpty()) {
            throw new AttributeMissingException( "Venue", "name" );
        }
        width = getIntegerAttribute( element, "width" );
        depth = getIntegerAttribute( element, "depth" );
        height = getIntegerAttribute( element, "height" );

        // Record the extreme point.
        new Point( width, depth, height );

        StaticVenue = this;
    }

    /**
     * Determine if the provided {@code Box} fits entirely within the {@code Venue}.
     *
     * @param box (Hopefully) inner {@code Box}.
     * @return true if inner {@code Box} fits entirely within the Venue.
     * @since 0.0.6
     */
    public static boolean Contains( Box box ) {
        Box room = new Box( new Point( 0, 0, 0 ), StaticVenue.width, StaticVenue.depth,
                            StaticVenue.height );
        return room.contains( box );
    }

    /**
     * Confirm that a specified rectangle fits into this venue.
     *
     * @param rectangle area to check
     * @return true if specified rectangle fits into this {@code StaticVenue}.
     */
    public static boolean Contains2D( Rectangle rectangle ) {

        Rectangle area = new Rectangle( 0, 0, StaticVenue.width, StaticVenue.depth );

        return area.contains( rectangle );
    }

    /**
     * Confirm that a specified set of x, y coordinates is located within this venue.
     *
     * @param x coordinate to check
     * @param y coordinate to check
     * @return outcode {@link Rectangle result of outcode call}
     */
    public static int Contains2D( int x, int y ) {

        Rectangle area = new Rectangle( 0, 0, StaticVenue.width, StaticVenue.depth );

        return area.outcode( x, y );
    }

    /**
     * Provides the height of the venue.
     *
     * @return height of the venue
     */
    public static int Width() {
        System.err.println( "StaticVenue (" + StaticVenue.name + ").width: " + StaticVenue.width );
        return StaticVenue.width;
    }

    /**
     * Provides the height of the venue.
     *
     * @return height of the venue
     */
    public static int Depth() {
        System.err.println( "StaticVenue (" + StaticVenue.name + ").depth: " + StaticVenue.depth );
        return StaticVenue.depth;
    }

    /**
     * Provides the height of the venue.
     *
     * @return height of the venue
     */
    public static int Height() {
        System.err.println(
                "StaticVenue (" + StaticVenue.name + ").height: " + StaticVenue.height );
        return StaticVenue.height;
    }

    /**
     * Draw this {@code StaticVenue} to the provided canvas.
     *
     * @param canvas drawing region.
     */
    @Override
    public void drawPlan( Graphics2D canvas ) {
        canvas.setPaint( Color.BLACK );
        canvas.draw( new Rectangle( 0, 0, width, depth ) );
    }

    /**
     * Draw this {@code StaticVenue} to the provided canvas.
     *
     * @param canvas drawing region.
     */
    @Override
    public void drawSection( Graphics2D canvas ) {
        canvas.setPaint( Color.BLACK );
        canvas.draw( new Rectangle( 0, 0, depth, height ) );
    }

    /**
     * Draw this {@code StaticVenue} to the provided canvas.
     *
     * @param canvas drawing region.
     */
    @Override
    public void drawFront( Graphics2D canvas ) {
        canvas.setPaint( Color.BLACK );
        canvas.draw( new Rectangle( 0, 0, width, height ) );
    }

    /**
     * Make the venue's name into the title of the drawing.
     *
     * @param draw Provide access to the generated DOM.
     */
    @Override
    public void dom( Draw draw ) {
        draw.setDocumentTitle( name );
    }
}
