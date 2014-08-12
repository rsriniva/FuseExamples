<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">

<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:web="http://www.webserviceX.NET/">
   <soap:Header/>
   <soap:Body>
      <web:GetQuote>      
         <web:symbol><xsl:value-of select="/company/id"/></web:symbol>
      </web:GetQuote>
   </soap:Body>
</soap:Envelope>

</xsl:template>
</xsl:stylesheet>