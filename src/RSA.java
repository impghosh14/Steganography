import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA{
	private static final BigInteger one=new BigInteger("1");
	private static final SecureRandom random=new SecureRandom();
	private BigInteger publicKey;
	private BigInteger privateKey;
	private BigInteger modulus;
	private BigInteger phi;	
	private static final int N=50;

	public RSA(){
		
		BigInteger p=BigInteger.probablePrime(N/2,random);
		BigInteger q=BigInteger.probablePrime(N/2,random);
		//System.out.println("p: "+p+"\nq: "+q);

		modulus=p.multiply(q);
		//System.out.println("modulus: "+modulus);

		BigInteger x=p.subtract(one);
		BigInteger y=q.subtract(one);
		phi=x.multiply(y);
		//System.out.println("phi: "+phi);

		//65536+1 as public key for common practice
		publicKey=new BigInteger("65537");
		//System.out.println("publicKey: "+publicKey);

		privateKey=publicKey.modInverse(phi);
		//System.out.println("privateKey: "+privateKey);
	}
	public BigInteger encrypt(BigInteger original){
		BigInteger encrypted=original.modPow(publicKey,modulus);
		return encrypted;
	}
	public BigInteger decrypt(BigInteger encrypted){
		BigInteger decrypted=encrypted.modPow(privateKey,modulus);
		return decrypted;
	}

	public BigInteger getPrivateKey(){
		return privateKey;
	}
	public BigInteger getModulus(){
		return modulus;
	}
	
	
}