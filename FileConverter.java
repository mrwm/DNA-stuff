package dna;

import java.io.*;
import java.util.*;


public class FileConverter
{

	private File fastq, fasta;

	//ctor
	public FileConverter(File fq, File fa){
		this.fastq = fq;
		this.fasta = fa;
	}

	//
	// Writes a fasta file consisting of conversion of all records from the fastq with
	// sufficient quality and unique defline.
	//
	public void convert() throws IOException
	{

		// Build chain of readers.
		FileReader fr = new FileReader(fastq);
		BufferedReader br = new BufferedReader(fr);
		FastqReader fqr = new FastqReader(br);

		// Build chain of writers.
		FileWriter fw = new FileWriter(fasta);
		PrintWriter pw = new PrintWriter(fw);
		FastaWriter faw = new FastaWriter(pw);

		//boolean done = false;
		while (true){
			try{
				// Read, translate, write.
				FastqRecord fqRec = fqr.readRecord();
				if (fqRec == null){
					break;
				}
				if (!fqRec.qualityIsLow()){
					//FastaRecord faRec = new FastaRecord(fqRec);
					//faw.writeRecord(faRec);
					faw.writeRecord(new FastaRecord(fqRec));
				}

			}
			//catch(IOException e){
				//Something happens
			//}
			catch (RecordFormatException e) {
				//Auto-generated catch block
				//e.printStackTrace();
			}
		}
		// Close fr, br, fw, and pw in reverse order of creation.
		pw.close();
		fw.close();
		br.close();
		fr.close();
	}



	public static void main(String[] args)
	{
		System.out.println("Starting");
		try
		{
			File fastq = new File("data/HW4.fastq");
			if (!fastq.exists())
			{
				System.out.println("Can't find input file " + fastq.getAbsolutePath());
				System.exit(1);
			}
			File fasta = new File("data/HW4.fasta");
			FileConverter converter = new FileConverter(fastq, fasta);
			converter.convert();
		}
		catch (IOException x)
		{
			System.out.println(x.getMessage());
		}
		System.out.println("Done");
	}
}
