import io.javalin.Javalin
import org.starcoin.rsa.RSAAccumulator
import org.starcoin.rsa.TwoValue
import java.math.BigInteger

data class VerifyBody(val commitment: String, val first: String, val second: String) {
	var proof:  TwoValue<BigInteger> = TwoValue<BigInteger>(BigInteger(first),BigInteger(second))
}

class ProofResponse(response: TwoValue<BigInteger>) {
	val first: String = ""+response.first
	val second: String = ""+response.second
}

fun main() {
	val acc = RSAAccumulator()
	val app = Javalin.create().start(7000)
	app.get("/commitment") { ctx ->
		ctx.result(acc.commitment.toString(10))
	}
	app.put("/add/:member") { ctx ->
		val member = ctx.pathParam("member");
		acc.add(BigInteger(member))
		ctx.json(acc.proveMembership(BigInteger(member)))
	}
	app.post("/verify") { ctx ->
		val body = ctx.bodyAsClass(VerifyBody::class.java)
		ctx.json(
			RSAAccumulator
				.verifyMembership(
					BigInteger(body.commitment),
					body.proof
				)
		)
	}
}