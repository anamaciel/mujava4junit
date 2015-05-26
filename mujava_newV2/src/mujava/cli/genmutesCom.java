/**
 * Copyright (C) 2015  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 



package mujava.cli;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
 /**
 * <p>
 * Description: Pre-defined arguments options for genmutes command
 * </p>
 * 
 * @author Lin Deng
 * @version 1.0  
  */
class genmutesCom {
	  @Parameter
	  private List<String> parameters = new ArrayList<String>();
	 	  
	  
	  @Parameter(names = "-AOIS", description = "Generate mutants of AOIS")
	  private boolean AOIS;
	 
	  @Parameter(names = "-all",  description = "Generate mutants of ALL MUTATION OPERATORS")
	  private boolean all;
	  
	  @Parameter(names = "-D",  description = "Generate mutants of ALL classes in the directory")
	  private boolean D;
	  
	  
	  @Parameter(names = "--help", help = true)
	  private boolean help;
	  
	  
	  @Parameter(names = "-debug", description = "Debug mode")
	  private boolean debug = false;
	  
	  
	  
public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}


	public List<String> getParameters()
	{
		return parameters;
	}
	public void setParameters(List<String> parameters)
	{
		this.parameters = parameters;
	}

	
	public boolean getAOIS()
	{
		return AOIS;
	}
	public void setAOIS(boolean aOIS)
	{
		AOIS = aOIS;
	}
	
	public boolean getAll()
	{
		return all;
	}
	public void setAll(boolean all)
	{
		this.all = all;
	}
	  
	public boolean getD()
	{
		return D;
	}
	
	public void setD(boolean D)
	{
		this.D = D;
	}
	 
	
	  
	}