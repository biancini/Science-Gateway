<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:jsdl="http://schemas.ggf.org/jsdl/2005/11/jsdl"
                xmlns:posix="http://schemas.ggf.org/jsdl/2005/11/jsdl-posix"
                xmlns:spmd="http://schemas.ogf.org/jsdl/2007/02/jsdl-spmd"
                xmlns:ext="http://www.in2p3.fr/jsdl-extension">
    <xsl:output method="text"/>
    <xsl:param name="ShellPath"/>

    <!-- entry point (MUST BE RELATIVE) -->
    <xsl:template match="jsdl:JobDefinition">
        <xsl:apply-templates select="jsdl:JobDescription"/>
    </xsl:template>

    <xsl:template match="jsdl:JobDescription">&amp;<xsl:text/>
        <!-- executable and arguments -->
        <xsl:choose>
            <xsl:when test="$ShellPath">
(executable = <xsl:value-of select="$ShellPath"/>)<!-- NOTE: '"..."' enables late evaluation of env variables -->
(arguments = -c '"<xsl:value-of select="jsdl:Application/posix:POSIXApplication/posix:Executable/text()"/>
                <xsl:if test="jsdl:Application/posix:POSIXApplication/posix:Argument/text()">
                    <!-- NOTE: use \"$MYVAR\" if $MYVAR contains space characters -->
                    <xsl:for-each select="jsdl:Application/posix:POSIXApplication/posix:Argument/text()">
                        <xsl:text> </xsl:text><xsl:value-of select="."/>
                    </xsl:for-each>
                </xsl:if>"')<xsl:text/>
            </xsl:when>
            <xsl:otherwise>
(executable = <xsl:value-of select="jsdl:Application/posix:POSIXApplication/posix:Executable/text()"/>)<xsl:text/>
                <xsl:if test="jsdl:Application/posix:POSIXApplication/posix:Argument/text()">
(arguments =<xsl:for-each select="jsdl:Application/posix:POSIXApplication/posix:Argument/text()">
                    <xsl:text> </xsl:text><xsl:value-of select="."/>
                </xsl:for-each>)<xsl:text/>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>
<!--  two_phase=time out : timeout indicates the timeout to send COMMIT and END signal  -->
(two_phase=300)<xsl:text/>
		<xsl:choose>
            <xsl:when test="jsdl:Application/posix:POSIXApplication/posix:Output/text()">
(stdout = <xsl:value-of select="jsdl:Application/posix:POSIXApplication/posix:Output/text()"/>)<xsl:text/>
            </xsl:when>
            <xsl:otherwise>
            <!--  just for debug -->
(stdout = stdout.txt)<xsl:text/>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:choose>
            <xsl:when test="jsdl:Application/posix:POSIXApplication/posix:Error/text()">
(stderr = <xsl:value-of select="jsdl:Application/posix:POSIXApplication/posix:Error/text()"/>)<xsl:text/>
            </xsl:when>
            <xsl:otherwise>
            <!--  just for debug  -->
(stderr = stderr.txt)<xsl:text/>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:for-each select="jsdl:Application/posix:POSIXApplication/posix:Input/text()">
(stdin = <xsl:value-of select="."/>)<xsl:text/>
        </xsl:for-each>
        <!-- other -->
        <xsl:if test="jsdl:Application/posix:POSIXApplication/posix:Environment">
(environment = <xsl:for-each
                select="jsdl:Application/posix:POSIXApplication/posix:Environment">(<xsl:value-of
                select="@name"/><xsl:text> </xsl:text>"<xsl:value-of select="text()"/>")</xsl:for-each>)<xsl:text/>
        </xsl:if>
        <xsl:for-each select="jsdl:Application/posix:POSIXApplication/posix:WorkingDirectory/text()">
(directory = <xsl:value-of select="."/>)<xsl:text/>
        </xsl:for-each>
       <xsl:for-each select="jsdl:Resources/jsdl:TotalPhysicalMemory/jsdl:UpperBoundedRange/text()">
(maxMemory = <xsl:value-of select="."/>)<xsl:text/>
        </xsl:for-each>
        <xsl:for-each select="jsdl:Resources/jsdl:TotalCPUTime/jsdl:UpperBoundedRange/text()">
(maxCpuTime = <xsl:value-of select=". div 60"/>)<xsl:text/>
        </xsl:for-each>
        <xsl:for-each select="jsdl:Application/spmd:SPMDApplication/spmd:SPMDVariation/text()[not(. = 'None')]">
        	<xsl:choose>
           		<xsl:when test=". = 'MPI'">
(jobType = mpi)<xsl:text/>
           		</xsl:when>
            	<xsl:otherwise>
	            	<xsl:message terminate="yes">Unsupported SPMDVariation : <xsl:value-of select="."/></xsl:message>
            	</xsl:otherwise>
        	</xsl:choose>
        </xsl:for-each>
        <xsl:for-each select="jsdl:Application/spmd:SPMDApplication/spmd:NumberOfProcesses/text()">
(count = <xsl:value-of select="."/>)<xsl:text/>
        </xsl:for-each>
        <xsl:for-each select="jsdl:Application/spmd:SPMDApplication">
	        <xsl:if test="spmd:ProcessesPerHost/text()">
(hostCount = <xsl:value-of select="ceiling(spmd:NumberOfProcesses/text() div spmd:ProcessesPerHost/text())"/>)<xsl:text/>
        	</xsl:if>
        </xsl:for-each>
        <xsl:for-each select="jsdl:JobIdentification/jsdl:JobAnnotation/text()">
(queue = <xsl:value-of select="."/>)<xsl:text/>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>