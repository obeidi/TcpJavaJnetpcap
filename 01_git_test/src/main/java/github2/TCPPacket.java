package github2 ;

import java.io.ByteArrayOutputStream ;
import java.io.IOException ;

// TODO
// TCPPacket: represent a TCP  packet
public class TCPPacket{

	private TCPHeader header ;
	private byte[] data ;

	public TCPPacket( TCPHeader header ){
		this.header = header ; 
	}

	// convert the entire packet (data + header) to a byte array
	public byte[] toByteArray(){

		byte[] headerBytes = header.toByteArray() ;
		ByteArrayOutputStream out = new ByteArrayOutputStream( );

		try{
			out.write( headerBytes ) ;
		
			if( this.data != null ){
			    out.write( data ) ;
			}
			
			return out.toByteArray() ;
		
		}
		catch(IOException ex){
			System.out.println( ex.toString() ) ;
		}

		return null ;
	}

	public int length(){
		return header.length() + ( this.data == null ? 0 : this.data.length );
	}

	public void setData( byte[] data ){
		this.data = data ;
	}

	public byte[] getData(){
		return this.data ;
	}

	public void setHeader( TCPHeader header ){
		this.header = header ;
	}

	public TCPHeader getHeader(){
		return this.header ;
	}

    public boolean isAckPacket(){
        return this.header.isACKFlagOn() ;
    }

    public boolean isFinPacket(){
        return this.header.isFINFlagOn() ;
    }

    public boolean isSynPacket(){
        return this.header.isSYNFlagOn() ;
    }

    public boolean containData(){
        return this.data != null ;
    }
}


