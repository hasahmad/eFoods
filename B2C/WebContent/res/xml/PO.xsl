<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">	
	<xsl:template match="/">
		<html>
			<head>
				<title>eFoods - Checkout</title>
			</head>
			<body>
				<p><a href='Home'>Back to Shop</a></p>
				<h1>Purchase Order Confirmation</h1>
				<h3>Hello <xsl:value-of select="order/customer/name" /></h3>
				<h4>Your order has been recieved.</h4>
				<p><strong>Account: </strong><xsl:value-of select="order/customer/@account" /></p>
				<p><strong>Submitted on: </strong><xsl:value-of select="order/@submitted" /></p>
				
				<table border="1" cellpadding="4" width="100%">
					<tr>
						<th>Product Number</th>
						<th>Product Name</th>
						<th>Price</th>
						<th>Extended Price</th>
						<th>Quantity</th>
					</tr>
					<xsl:for-each select="order/items/item">
						<tr>
							<td>
								<xsl:value-of select="@number" />
							</td>
							<td>
								<xsl:value-of select="name" />
							</td>
							<td>
								<xsl:value-of select="price" />
							</td>
							<td>
								<xsl:value-of select="extended" />
							</td>
							<td>
								<xsl:value-of select="quantity" />
							</td>
						</tr>
					</xsl:for-each>
				</table>
				<hr />
				<h3>Summary</h3>
				<h4>Total: <xsl:value-of select="order/total" /></h4>
				<h4>Shipping: <xsl:value-of select="order/shipping" /></h4>
				<h4>HST: <xsl:value-of select="order/HST" /></h4>
				<h4>Grand Total: <xsl:value-of select="order/grandTotal" /></h4>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>
