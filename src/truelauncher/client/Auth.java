/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 */

package truelauncher.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import truelauncher.utils.LauncherUtils;

public class Auth {

	public static void sendAuth1(final String hostname, final int port, final int protocolversion, final String nick, final String token, final String password)
	{
		new Thread()
		{
			public void run()
			{
				//
				//fake handshake packet format.
				//nick = AuthConnector
				//host = authpacket(nick + token + password)
				//port = port
				//
				try {
					//establish connection
					Socket socket = new Socket(hostname, port);
					socket.setSoTimeout(30000);
					socket.setTrafficClass(24);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					//write handshake packet
					//write packet id
					dos.write(2);
					//write protocolVersion
					dos.writeByte(protocolversion);
					//write name;
					writeString(dos, "AuthConnector");
					//write authpacket instead of hostname
					String authpacket = nick+"|"+token+"|"+password;
					writeString(dos, authpacket);
					//write port
					dos.writeInt(port);
					socket.close();	
				} catch (Exception e) {
					LauncherUtils.logError(e);
				}
			}
		}.start();
	}

	public static void sendAuth2(String hostname, int port, int protocolversion, String nick, String token, String password)
	{
		
	}
	
	
	private static void writeString(DataOutputStream dos, String string) throws IOException
	{
		dos.writeShort(string.length());
		dos.writeChars(string);
	}
	
}
