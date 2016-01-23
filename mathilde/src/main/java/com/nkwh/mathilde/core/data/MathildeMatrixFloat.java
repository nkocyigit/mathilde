package com.nkwh.mathilde.core.data;

import com.nkwh.mathilde.res.InternationalizationManager;

public final class MathildeMatrixFloat
{
	private int _rowCount;
	private int _colCount;
	private float[][] _data;
	
	public MathildeMatrixFloat(int rowCount, int colCount)
	{
		this._rowCount = rowCount;
		this._colCount = colCount;
		_data = new float[rowCount][colCount];
	}
	
	public MathildeMatrixFloat(int rowCount, int colCount, float[][] data) 
	{
		this._rowCount = rowCount;
		this._colCount = colCount;
		this._data = data;
	};

	public String getTypeName() 
	{
		return InternationalizationManager.getResourceBundle().getString("str_float_matrix");
	}
	
	public float[][] getMatrixData()
	{
		return _data;
	}
	
	public String printMatrix() 
	{
		String format = "%8.3f";
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < _rowCount; i++)
		{
			for (int j = 0; j < _colCount; j++) 
			{
				sb.append(String.format(format, _data[i][j]));
				sb.append(" ");
			}
			sb.append(System.lineSeparator());
		}
		
		return sb.toString();
	}
	
	public static MathildeMatrixFloat randomMatrix(int rowCount, int colCount) 
	{
		float[][] resData = new float[rowCount][colCount];
		for(int i = 0; i < rowCount; i++)
			for(int j = 0; j < colCount; j++)
				resData[i][j] = (float) Math.random();
	
		return new MathildeMatrixFloat(rowCount, colCount, resData);
	}

	public int getRowCount() 
	{
		return _rowCount;
	}
	
	public int getColCount() 
	{
		return _colCount;
	}
}
