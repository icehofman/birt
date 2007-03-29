/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.item.crosstab.internal.ui.views.provider;

import org.eclipse.birt.report.designer.internal.ui.views.DefaultNodeProvider;
import org.eclipse.birt.report.item.crosstab.core.IMeasureViewConstants;
import org.eclipse.birt.report.item.crosstab.core.de.MeasureViewHandle;
import org.eclipse.birt.report.item.crosstab.internal.ui.util.CrosstabUIHelper;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.swt.graphics.Image;

public class MeasureSubNodeProvider extends DefaultNodeProvider
{

	public Object[] getChildren( Object model )
	{
		PropertyHandle handle = (PropertyHandle) model;
		ExtendedItemHandle element = (ExtendedItemHandle) handle.getElementHandle( );
		try
		{
			MeasureViewHandle measure = (MeasureViewHandle) element.getReportItem( );
			String propertyName = handle.getPropertyDefn( ).getName( );
			Object value = handle.getValue( );
			if ( value == null )
				return new Object[0];

			if ( propertyName.equals( IMeasureViewConstants.HEADER_PROP ) )
			{
				return new Object[]{
					measure.getHeader( ).getModelHandle( )
				};
			}
			else if ( propertyName.equals( IMeasureViewConstants.DETAIL_PROP ) )
			{
				return new Object[]{
					measure.getCell( ).getModelHandle( )
				};
			}
			else if ( propertyName.equals( IMeasureViewConstants.AGGREGATIONS_PROP ) )
			{
				int count = measure.getAggregationCount( );
				Object[] aggs = new Object[count];
				for ( int i = 0; i < count; i++ )
				{
					aggs[i] = measure.getAggregationCell( i ).getModelHandle( );
				}
				return aggs;
			}
		}
		catch ( ExtendedElementException e )
		{
		}
		return new Object[0];
	}

	public Object getParent( Object model )
	{
		PropertyHandle handle = (PropertyHandle) model;
		return handle.getElementHandle( );
	}

	public boolean hasChildren( Object model )
	{
		return getChildren( model ).length != 0;
	}

	public String getNodeDisplayName( Object element )
	{
		PropertyHandle handle = (PropertyHandle) element;
		String propertyName = handle.getPropertyDefn( ).getName( );

		if ( propertyName.equals( IMeasureViewConstants.HEADER_PROP ) )
		{
			return "Header";
		}
		else if ( propertyName.equals( IMeasureViewConstants.DETAIL_PROP ) )
		{
			return "Detail";
		}
		else if ( propertyName.equals( IMeasureViewConstants.AGGREGATIONS_PROP ) )
		{
			return "Aggregation";
		}
		return super.getNodeDisplayName( element );
	}

	public Image getNodeIcon( Object element )
	{
		PropertyHandle handle = (PropertyHandle) element;
		String propertyName = handle.getPropertyDefn( ).getName( );
		if ( propertyName.equals( IMeasureViewConstants.HEADER_PROP ) )
		{
			return CrosstabUIHelper.getImage( CrosstabUIHelper.HEADER_IMAGE );
		}
		else if ( propertyName.equals( IMeasureViewConstants.DETAIL_PROP ) )
		{
			return CrosstabUIHelper.getImage( CrosstabUIHelper.DETAIL_IMAGE );
		}
		else if ( propertyName.equals( IMeasureViewConstants.AGGREGATIONS_PROP ) )
		{
			return CrosstabUIHelper.getImage( CrosstabUIHelper.AGGREGATION_IMAGE );
		}
		return super.getNodeIcon( element );
	}
}
