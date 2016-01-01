#import "MyOpenGLView.h"
#include <OpenGL/gl.h>

@implementation MyOpenGLView

static void drawAnObject ()
{
    glLoadIdentity();
    glTranslatef(-1.5f,0.0f,-6.0f);
    glColor3f(1.0f, 0.85f, 0.35f);
    glBegin(GL_TRIANGLES);
    {
        glVertex3f( 0.0f, 1.0f, 0.0f);              // Top
        glVertex3f(-1.0f,-1.0f, 0.0f);              // Bottom Left
        glVertex3f( 1.0f,-1.0f, 0.0f);              // Bottom Right
    }
    glEnd();
    glTranslatef(3.0f,0.0f,0.0f);
    glBegin(GL_QUADS);
    {
        glVertex3f(-1.0f, 1.0f, 0.0f);              // Top Left
        glVertex3f( 1.0f, 1.0f, 0.0f);              // Top Right
        glVertex3f( 1.0f,-1.0f, 0.0f);              // Bottom Right
        glVertex3f(-1.0f,-1.0f, 0.0f);              // Bottom Left
    }
    glEnd();
}

-(void) drawRect: (NSRect) bounds
{
    glShadeModel( GL_SMOOTH );                // Enable smooth shading
    glClearColor( 0.0f, 0.0f, 0.0f, 0.5f );   // Black background
    glClearDepth( 1.0f );                     // Depth buffer setup
    glEnable( GL_DEPTH_TEST );                // Enable depth testing
    glDepthFunc( GL_LEQUAL );                 // Type of depth test to do
    glHint( GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST );
    
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
    
    drawAnObject();
    glFlush();
}
@end

