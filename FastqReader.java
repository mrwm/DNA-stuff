package dna;

import java.io.*;


//
// Reads lines from a BufferedReader and builds a FastqRecord.
//


public class FastqReader
{
	private BufferedReader theBufferedReader;

	public FastqReader(BufferedReader br){
		this.theBufferedReader = br;
	}

	// Returns next record in the file, or null if EOF (end-of-file).
	public FastqRecord readRecord() throws IOException, RecordFormatException
	{
		// Read the defline from the BufferedReader. Return null if you read null,
		// indicating end of file.
		String buff = theBufferedReader.readLine();

		if (buff == null){return null;}
		if (buff.charAt(0) != '@'){
			throw new RecordFormatException("Some weird thing");
		}
		else {
			// Read the next 3 lines from the buffered reader. Construct and return
			// a FastqRecord.
			String seqLine = theBufferedReader.readLine();
			theBufferedReader.readLine(); //Skip the "+" line
			String qualLine = theBufferedReader.readLine();
			FastqRecord out = new FastqRecord(buff, seqLine, qualLine);
			return out;

		}

	}
}
