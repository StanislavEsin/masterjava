<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:p="http://javaops.ru" exclude-result-prefixes="p"
                version="1.0">

    <xsl:param name="projectName"/>
    <xsl:template match="/">
        <html>
            <body>
                <h1>Projects</h1>

                <table border="1">
                    <tr><td align="center">Project</td><td align="center">Group</td></tr>
                    <xsl:if test="$projectName != ''">
                        <xsl:apply-templates select="p:Payload/p:Projects/p:Project[@name=$projectName]"/>
                    </xsl:if>
                    <xsl:if test="$projectName = ''">
                        <xsl:apply-templates select="p:Payload/p:Projects/p:Project[@name]"/>
                    </xsl:if>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="p:Project">
        <tr>
            <td><xsl:value-of select="attribute::name"/></td>
            <td><xsl:apply-templates select="p:Groups"/></td>
        </tr>
    </xsl:template>

    <xsl:template match="p:Group">
        <div>
            <xsl:value-of select="attribute::name"/>
        </div>
    </xsl:template>
</xsl:stylesheet>