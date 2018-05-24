<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:p="http://javaops.ru" exclude-result-prefixes="p"
                version="1.0">

    <xsl:template match="/">
        <html>
            <body>
                <h1>Members</h1>
                <table border="1">
                    <tr><td align="center">Name</td><td align="center">Email</td></tr>
                    <xsl:apply-templates select="p:Payload/p:Users/p:User"/>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="p:User">
        <tr>
            <td><xsl:value-of select="self::p:User"/></td>
            <td><xsl:value-of select="attribute::email"/></td>
        </tr>
    </xsl:template>
</xsl:stylesheet>