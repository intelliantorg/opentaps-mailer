/*
 * Copyright (c) 2006 - 2009 Open Source Strategies, Inc.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the Honest Public License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Honest Public License for more details.
 *
 * You should have received a copy of the Honest Public License
 * along with this program; if not, write to Funambol,
 * 643 Bair Island Road, Suite 305 - Redwood City, CA 94063, USA
 */

package org.opentaps.gwt.common.client.listviews;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opentaps.gwt.common.client.UtilUi;
import org.opentaps.gwt.common.client.events.LoadableListener;
import org.opentaps.gwt.common.client.events.LoadableListenerAdapter;
import org.opentaps.gwt.common.client.form.FormNotificationInterface;
import org.opentaps.gwt.common.client.form.ServiceErrorReader;
import org.opentaps.gwt.common.client.form.base.BaseFormPanel;
import org.opentaps.gwt.common.client.lookup.Permissions;
import org.opentaps.gwt.common.client.lookup.UtilLookup;
import org.opentaps.gwt.common.client.suggest.EntityAutocomplete;
import org.opentaps.gwt.common.client.suggest.EntityStaticAutocomplete;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.SortDir;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.BooleanFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.data.event.StoreListener;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GridView;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.RowParams;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;

/**
 * The base class for tables that list entities and that support AJAX
 * sorting, pagination, filtering, and in-place edition.
 */
public abstract class EntityEditableListView extends EditorGridPanel implements FormNotificationInterface, StoreListener {

    private static final String MODULE = EntityEditableListView.class.getName();

    private PagingToolbar pagingToolbar;
    private ColumnModel columnModel;
    private Map<String, String> filters = new HashMap<String, String>();

    private HttpProxy proxy;
    private JsonReader reader;
    private String queryUrl;
    private Store store;
    private RecordDef recordDef;
    private RowSelectionModel selectionModel = new RowSelectionModel(true);

    private Set<String> recordPrimaryKeyFields;
    private Set<FieldDef> fieldDefinitions = new HashSet<FieldDef>();
    private List<ColumnConfig> columnConfigs = new ArrayList<ColumnConfig>();
    private List<LinkColumnConfig> lookupColumns = new ArrayList<LinkColumnConfig>();

    // keeps track of the autocompleters that we should wait to be loaded before loading the grid
    // see the timers below
    private List<EntityAutocomplete> autocompletes = new ArrayList<EntityAutocomplete>();

    // used to store the latest combo box changed display value
    // and so that once a cell is edited, the autocompleter display string is displayed instead of the real value
    private boolean displayStringChanged = false;
    private String displayString;

    // if the buttons are created with makeCreateUpdateColumn / makeDeleteColumn those will have the column index set
    // they are used in the cell click handler to figure out which button was clicked
    private int createUpdateIndex = -1;
    private int deleteIndex = -1;

    // default values for creating a new row
    // use setDefaultValue to override the default nulls
    // use setFirstEditableColumn to set which column editor should open after a new row was inserted
    private Object[] defaultValuesArray;
    private Map<String, Object> defaultValues = new HashMap<String, Object>();

    // use canCreateNewRow to allow record creation
    private Boolean canCreateNewRow;

    // the global permissions as parsed from the service response
    private Permissions globalPermissions;

    // set this to false to force a non editable grid
    private boolean editable = true;

    // set this to true to use a paging toolbar
    private boolean usePagingToolbar = false;

    // set to auto insert a summary row on load
    private boolean useSummaryRow = false;

    // set this to false if you need to apply filters before loading the data in order to avoid loading the data twice
    private boolean autoLoad = true;

    // the default page size when the grid and pager are initialized
    private int defaultPageSize = UtilLookup.DEFAULT_LIST_PAGE_SIZE;

    private List<LoadableListener> listeners = new ArrayList<LoadableListener>();

    // the URL to post batch data to, set when adding the save all button
    private String saveAllUrl;
    // additional data to be added to each record when batch posted
    private Map<String, String> additionalBatchData;

    // store Records for batch delete actions
    private List<Record> toDeleteRecords = new ArrayList<Record>();

    private boolean loaded = false;
    private boolean loadNow = false;

    /**
     * Default constructor.
     */
    public EntityEditableListView() {
        this(null);
    }

    /**
     * Constructor giving a title for this list view, which is displayed in the UI.
     * @param title the title of the list
     */
    public EntityEditableListView(String title) {
        if (title != null) {
            setTitle(title);
        }

        setFrame(true);
        setStripeRows(true);
        setAutoHeight(true);
        setCollapsible(true);

        setSelectionModel(selectionModel);
        setClicksToEdit(1);
    }

    /**
     * Configures this list view according to previously created column.
     * @param url the URL used to populate the list view
     * @param defaultSortField the name of field to sort by default
     * @see #makeColumn
     */
    protected void configure(String url, String defaultSortField) {
        configure(url, defaultSortField, SortDir.ASC);
    }

    /**
     * Configures this list view according to previously created column.
     * @param url the URL used to populate the list view
     * @param defaultSortField the name of field to sort by default
     * @param defaultSortDirection the default sort direction
     * @see #makeColumn
     */
    protected void configure(String url, String defaultSortField, SortDir defaultSortDirection) {
        configure(makeRecordDef(), makeColumnModel(), url, defaultSortField, defaultSortDirection);
    }

    protected void configure(RecordDef recordDef, ColumnModel columnModel, String url, String defaultSortField) {
        configure(recordDef, columnModel, url, defaultSortField, SortDir.ASC);
    }

    protected void configure(RecordDef recordDef, ColumnModel columnModel, String url, String defaultSortField, SortDir defaultSortDirection) {

        this.recordDef = recordDef;
        this.columnModel = columnModel;
        this.queryUrl = url;
        reader = new JsonReader(recordDef);
        reader.setRoot(UtilLookup.JSON_ROOT);
        reader.setId(UtilLookup.JSON_ID);
        reader.setTotalProperty(UtilLookup.JSON_TOTAL);

        proxy = new HttpProxy(queryUrl);

        store = new Store(proxy, reader, true);
        store.setDefaultSort(defaultSortField, defaultSortDirection);

        setStore(store);
        setColumnModel(columnModel);

        store.addStoreListener(this);

        if (usePagingToolbar) {
            try {
				makePagingToolbar(true);
			} catch (RequestException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }

        addEditorGridListener(new EditorGridListenerAdapter() {

                // check permissions before edit
                @Override public boolean doBeforeEdit(GridPanel grid, Record record, String field, Object value, int rowIndex, int colIndex) {
                    // check the grid global flag first
                    if (!editable) {
                        return false;
                    }

                    return Permissions.canUpdate(record);
                }

                // set cell edit handler, to sync displayed value when changed from a non-static autocompleter
                @Override public void onAfterEdit(GridPanel grid, Record record, String field, Object newValue, Object oldValue, int rowIndex, int colIndex) {
                    if (displayStringChanged) {
                        // copy displayString to the description field of the edited record
                        record.set(field + UtilLookup.DESCRIPTION_SUFFIX, displayString);
                        displayStringChanged = false;
                    }
                }
            });

        // set cell click handler for update / create and delete columns
        addGridCellListener(new GridCellListenerAdapter() {
                @Override public void onCellClick(GridPanel grid, int rowIndex, int colindex, EventObject e) {
                    // check the grid global flag
                    if (!editable) {
                        return;
                    }

                    Record rec = store.getRecordAt(rowIndex);
                    if (rec == null) {
                        return;
                    }

                    if (UtilUi.isSummary(rec)) {
                        return;
                    }
                    if (Permissions.canUpdate(rec) && colindex == createUpdateIndex) {
                        doUpdateCreateAction(rec);
                    } else if (Permissions.canDelete(rec) && colindex == deleteIndex) {
                        doDeleteAction(rec);
                    }
                }
            });

        if (autoLoad) {
            UtilUi.logDebug("Auto loading data.", MODULE, "configure");
            loadFirstPage();
        }
        GridView view = new GridView() {
                @Override public String getRowClass(Record record, int index, RowParams rowParams, Store store) {
                    String body = getRowBody(record, index);
                    if (body != null && !"".equals(body.trim())) {
                        body = "<span style=\"margin-left:15px\">" + body + "</span>";
                        rowParams.setBody(body);
                        return "x-grid3-row-expanded";
                    } else {
                        rowParams.setBody("");
                        return "";
                    }
                }
            };
        view.setEnableRowBody(true);
        view.setForceFit(true);
        view.setAutoFill(true);
        setView(view);
    }

    @Override
    public void reconfigure(Store store, ColumnModel model) {
        super.reconfigure(store, model);
    }

    /**
     * Reconfigures the store and keep the current column model.
     * @param store a <code>Store</code> value
     */
    public void reconfigure(Store store) {
        reconfigure(store, columnModel);
    }

    /**
     * Clears the filters of this grid.
     */
    public void clearFilters() {
        for (String k : filters.keySet()) {
            filters.put(k, "");
        }
    }

    protected void setFilter(String columnName, String value) {
        filters.put(columnName, value);
    }

    /**
     * Applies the filters of this grid and reload at the first page.
     */
    public void applyFilters() {
        applyFilters(true);
    }

    /**
     * Applies the filters of this grid.
     * @param resetPager should the grid reloads at the first page
     */
    public void applyFilters(boolean resetPager) {
        List<UrlParam> params = new ArrayList<UrlParam>();
        for (String k : filters.keySet()) {
            params.add(new UrlParam(k, filters.get(k)));
        }
        UrlParam[] urlParams = new UrlParam[params.size()];
        store.setBaseParams(params.toArray(urlParams));
        if (resetPager) {
            UtilUi.logDebug("Applied filters, load requested.", MODULE, "applyFilters");
            loadFirstPage();
        }
    }

    /**
     * Loads the grid data.
     */
    public void loadFirstPage() {
        loadNow = true;
        // if all the registered autocompleters are already loaded, we can load now
        // else setting loadNow to true will trigger the load automatically once they are all loaded.
        if (!loadIfReady()) {
            UtilUi.logDebug("Waiting some required autocompleters to load, deferring loading data.", MODULE, "loadFirstPage");
        }
    }

    /**
     * Resets the pager setting to the first page and reloads the store associated to this list view.
     */
    private void loadFirstPageAsync() {
        List<UrlParam> params = new ArrayList<UrlParam>();
        // if the pager is disabled explicitly, pass the NO_PAGER option to the service so it knows not to paginate the results
        // else pass the paging parameters as defined in the pagingToolbar (user given defaultPageSize is set in the pagingToolbar at this point)
        if (!usePagingToolbar) {
            params.add(new UrlParam(UtilLookup.PARAM_NO_PAGER, "Y"));
        } else if (pagingToolbar != null) {
            params.add(new UrlParam(UtilLookup.PARAM_PAGER_START, 0));
            params.add(new UrlParam(UtilLookup.PARAM_PAGER_LIMIT, pagingToolbar.getPageSize()));
        }
        UrlParam[] urlParams = new UrlParam[params.size()];
        store.reload(params.toArray(urlParams));
    }

    private boolean checkAllLoaded() {
        // check is all autocompleters are loaded
        for (EntityAutocomplete autocomplete : autocompletes) {
            if (!autocomplete.isLoaded()) {
                return false;
            }
        }
        return true;
    }

    private boolean loadIfReady() {
        // check if we should load now
        if (!loadNow) {
            return false;
        }
        if (checkAllLoaded()) {
            UtilUi.logDebug("All required autocompleters ready, loading data.", MODULE, "loadIfReady");
            loadFirstPageAsync();
            return true;
        }
        return false;
    }

    /**
     * Registers an autocompleter, the grid will wait for it to be loaded before loading its data.
     * This is useful when some of the data displayed depend on other stores to be loaded to render properly.
     * @param autocompleter an <code>EntityAutocomplete</code> value
     */
    public void registerAutocompleter(EntityAutocomplete autocompleter) {
        if (autocompleter != null) {
            autocompleter.addLoadableListener(new LoadableListenerAdapter() {
                    @Override public void onLoad() {
                        loadIfReady();
                    }
                });
            autocompletes.add(autocompleter);
        }
    }

    /**
     * Gets the last added <code>ColumnConfig</code>.
     * @return the last added <code>ColumnConfig</code>
     */
    public ColumnConfig getColumn() {
        return columnConfigs.get(columnConfigs.size() - 1);
    }

    /**
     * Sets the hidden flag for the column with given ID.
     * @param id the column ID
     * @param hidden a <code>boolean</code> value
     */
    public void setColumnHidden(String id, boolean hidden) {
        ColumnModel m = getColumnModel();
        for (int i = 0; i < m.getColumnCount(); i++) {
            if (id.equals(m.getDataIndex(i))) {
                m.setHidden(i, hidden);
                return;
            }
        }
    }

    /**
     * Creates a display column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param renderer the <code>Renderer</code> instance
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeEditableColumn
     * @see #makeLinkColumn
     */
    protected ColumnConfig makeColumn(String label, Renderer renderer) {
        ColumnConfig col = new ColumnConfig();
        col.setHeader(label);
        col.setRenderer(renderer);
        columnConfigs.add(col);
        return col;
    }

    /**
     * Creates a data column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param definition a <code>FieldDef</code> value
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeEditableColumn
     * @see #makeLinkColumn
     */
    protected ColumnConfig makeColumn(String label, FieldDef definition) {
        return makeEditableColumn(label, definition, (GridEditor) null);
    }

    /**
     * Creates a data column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param definition a <code>FieldDef</code> value
     * @param field a <code>Field</code> instance from which to create the <code>GridEditor</code>
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     * @see #makeLinkColumn
     */
    protected ColumnConfig makeEditableColumn(String label, FieldDef definition, Field field) {
        return makeEditableColumn(label, definition, new GridEditor(field));
    }

    /**
     * Creates a data column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param definition a <code>FieldDef</code> value
     * @param staticAutocomplete an <code>EntityStaticAutocomplete</code> instance from which to create the <code>GridEditor</code>, also serves as the translator
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     * @see #makeLinkColumn
     */
    protected ColumnConfig makeEditableColumn(String label, FieldDef definition, EntityStaticAutocomplete staticAutocomplete) {
        staticAutocomplete.setEmptyText("");
        return makeEditableColumn(label, definition, new GridEditor(staticAutocomplete), staticAutocomplete, true);
    }

    /**
     * Creates a data column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param definition a <code>FieldDef</code> value
     * @param autocomplete an <code>EntityAutocomplete</code> instance from which to create the <code>GridEditor</code>, will also sync the display value to the description field
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     * @see #makeLinkColumn
     */
    protected ColumnConfig makeEditableColumn(String label, FieldDef definition, EntityAutocomplete autocomplete) {
        return makeEditableColumn(label, definition, autocomplete, (String) null);
    }

    /**
     * Creates a data column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param definition a <code>FieldDef</code> value
     * @param autocomplete an <code>EntityAutocomplete</code> instance from which to create the <code>GridEditor</code>, will also sync the display value to the description field
     * @param initialFormatter the String used to format the displayed string, {0} is the description from the record descriptionIndex, {1} is the id from the record dataIndex
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     * @see #makeLinkColumn
     */
    protected ColumnConfig makeEditableColumn(String label, FieldDef definition, EntityAutocomplete autocomplete, String initialFormatter) {
        autocomplete.setEmptyText("");
        ColumnConfig col = makeEditableColumn(label, definition, new GridEditor(autocomplete), null, true, initialFormatter);
        autocomplete.addListener(new ComboBoxListenerAdapter() {
                @Override public void onSelect(ComboBox comboBox, Record record, int index) {
                    // get the display value, we don't know which cell was edited, so save the value for later
                    displayString = record.getAsString(UtilLookup.SUGGEST_TEXT);
                    // marked it changed so the grid cell edit event will know to get it
                    displayStringChanged = true;
                }
            });
        return col;
    }

    /**
     * Creates a data column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param definition a <code>FieldDef</code> value
     * @param autocomplete an <code>EntityAutocomplete</code> instance from which to create the <code>GridEditor</code>
     * @param staticAutocomplete an <code>EntityAutocomplete</code> instance from which to create the translator, it will have to be made static meaning that all records will be fetched so this is not recommended
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     * @see #makeLinkColumn
     */
    protected ColumnConfig makeEditableColumn(String label, FieldDef definition, EntityAutocomplete autocomplete, EntityAutocomplete staticAutocomplete) {
        autocomplete.setEmptyText("");
        staticAutocomplete.makeStatic();
        return makeEditableColumn(label, definition, new GridEditor(autocomplete), staticAutocomplete, true);
    }

    /**
     * Creates a data column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param definition a <code>FieldDef</code> value
     * @param editor a <code>GridEditor</code> instance
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     * @see #makeLinkColumn
     */
    protected ColumnConfig makeEditableColumn(String label, FieldDef definition, GridEditor editor) {
        return makeEditableColumn(label, definition, editor, null, false);
    }

    /**
     * Creates a data column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param definition a <code>FieldDef</code> value
     * @param editor a <code>GridEditor</code> instance
     * @param autocomplete the <code>EntityAutocomplete</code> instance serving as the translator
     * @param useDescriptionColumn a flag to indicate if we should use a description column config
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     * @see #makeLinkColumn
     */
    private ColumnConfig makeEditableColumn(String label, FieldDef definition, GridEditor editor, EntityAutocomplete autocomplete, boolean useDescriptionColumn) {
        return makeEditableColumn(label, definition, editor, autocomplete, useDescriptionColumn, null);
    }

    /**
     * Creates a data column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param definition a <code>FieldDef</code> value
     * @param editor a <code>GridEditor</code> instance
     * @param autocomplete the <code>EntityAutocomplete</code> instance serving as the translator
     * @param useDescriptionColumn a flag to indicate if we should use a description column config
     * @param initialFormatter the String used to format the initial displayed string, {0} is the description from the record descriptionIndex, {1} is the id from the record dataIndex
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     * @see #makeLinkColumn
     */
    private ColumnConfig makeEditableColumn(String label, FieldDef definition, GridEditor editor, EntityAutocomplete autocomplete, boolean useDescriptionColumn, String initialFormatter) {
        fieldDefinitions.add(definition);

        ColumnConfig col;
        if (useDescriptionColumn) {
            if (autocomplete != null) {
                registerAutocompleter(autocomplete);
                col = new DescriptionColumnConfig(label, definition.getName(), autocomplete);
            } else {
                String descriptionField = definition.getName() + UtilLookup.DESCRIPTION_SUFFIX;
                fieldDefinitions.add(new StringFieldDef(descriptionField));
                if (initialFormatter != null) {
                	col = new DescriptionColumnConfig(label, definition.getName(), descriptionField, initialFormatter);
                } else {
                	col = new DescriptionColumnConfig(label, definition.getName(), descriptionField);
                }
            }
        } else {
        	col = new ColumnConfig(label, definition.getName());
        }

        if (editor != null) {
            col.setEditor(editor);
        }
        columnConfigs.add(col);
        return col;
    }

    protected void addFieldDefinition(FieldDef definition) {
        fieldDefinitions.add(definition);
    }

    /**
     * Creates a data column for this list view prior to configuring it which renders as a link to the given URL.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param valueDefinition a <code>FieldDef</code> value
     * @param url the URL to be used for making the link, a placeholder can be used in the string for the field data. For example <code>/crmsfa/control/viewContact?partyId={0}</code>
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     */
    protected ColumnConfig makeLinkColumn(String label, FieldDef valueDefinition, String url) {
        return makeLinkColumn(label, valueDefinition, url, false);
    }

    /**
     * Creates a data column for this list view prior to configuring it which renders as a link to the given URL.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param valueDefinition a <code>FieldDef</code> value
     * @param url the URL to be used for making the link, a placeholder can be used in the string for the field data. For example <code>/crmsfa/control/viewContact?partyId={0}</code>
     * @param lookup if <code>true</code> the link will be replaced by a javascript call that set the value to return when the widget is used as a lookup
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     */
    protected ColumnConfig makeLinkColumn(String label, FieldDef valueDefinition, String url, boolean lookup) {
        return makeLinkColumn(label, valueDefinition, valueDefinition, url, lookup);
    }

    /**
     * Creates a data column for this list view prior to configuring it which renders as a link to the given URL.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param idDefinition a <code>FieldDef</code> value
     * @param valueDefinition a <code>FieldDef</code> value
     * @param url the URL to be used for making the link, a placeholder can be used in the string for the ID data. For example <code>/crmsfa/control/viewContact?partyId={0}</code>
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     */
    protected ColumnConfig makeLinkColumn(String label, FieldDef idDefinition, FieldDef valueDefinition, String url) {
        return makeLinkColumn(label, idDefinition, valueDefinition, url, false);
    }

    /**
     * Creates a data column for this list view prior to configuring it.
     * This method internally creates the necessary corresponding <code>ColumnConfig</code>.
     * @param label the column title label
     * @param idDefinition a <code>FieldDef</code> value
     * @param valueDefinition a <code>FieldDef</code> value
     * @param url the URL to be used for making the link, a placeholder can be used in the string for the ID data. For example <code>/crmsfa/control/viewContact?partyId={0}</code>
     * @param lookup if <code>true</code> the link will be replaced by a javascript call that set the value to return when the widget is used as a lookup
     * @return the created <code>ColumnConfig</code> instance
     * @see #makeColumn
     */
    protected ColumnConfig makeLinkColumn(String label, FieldDef idDefinition, FieldDef valueDefinition, String url, boolean lookup) {
        if (fieldDefinitions == null) {
            fieldDefinitions = new HashSet<FieldDef>();
        }
        if (columnConfigs == null) {
            columnConfigs = new ArrayList<ColumnConfig>();
        }

        fieldDefinitions.add(valueDefinition);

        // the ID field definition might not have been added yet, this is safe since fieldDefinitions is a Set
        fieldDefinitions.add(idDefinition);

        LinkColumnConfig col = new LinkColumnConfig(label, idDefinition.getName(), valueDefinition.getName(), url, lookup);
        if (lookup) {
            lookupColumns.add(col);
        }

        columnConfigs.add(col);
        return col;
    }

    /**
     * Adds a reload button to the grid for reverting any changes made.
     */
    protected void makeReloadButton() {
        addButton(new Button("Revert", new ButtonListenerAdapter() {
                @Override public void onClick(Button button, EventObject e) {
                    loadFirstPage();
                }
            }));
    }

    /**
     * Adds a save all button to the grid for batch commit.
     * @param url the URL to post the batch data to
     */
    protected void makeSaveAllButton(String url) {
        makeSaveAllButton(url, null);
    }

    /**
     * Adds a save all button to the grid for batch commit.
     * @param url the URL to post the batch data to
     * @param additionalBatchData extra data that should be attache to each record when batch posting
     */
    protected void makeSaveAllButton(String url, Map<String, String> additionalBatchData) {
        this.additionalBatchData = additionalBatchData;
        addButton(new Button("Save All", new ButtonListenerAdapter() {
                @Override public void onClick(Button button, EventObject e) {
                    doBatchAction();
                }
            }));
        saveAllUrl = url;
    }

    /**
     * Creates the update button column, which can do update / create buttons.
     * @param idFieldName the field used as ID for the record, the button will be a create button if the id is <code>null</code>, else it will be an update button
     * @see #makeColumn
     */
    protected void makeCreateUpdateColumn(String idFieldName) {
        createUpdateIndex = columnConfigs.size();
        columnConfigs.add(new CreateUpdateColumnConfig(idFieldName));
    }

    /**
     * Creates the delete button column.
     * @param idFieldName the field used as ID for the record, the button will simply delete the row if the id is <code>null</code>, else it will have to post a delete request
     * @see #makeColumn
     */
    protected void makeDeleteColumn(String idFieldName) {
        deleteIndex = columnConfigs.size();
        columnConfigs.add(new DeleteColumnConfig(idFieldName));
    }

    /**
     * Creates the create / update and delete button columns.
     * @param idFieldName the field used as ID for the record
     * @see #makeCreateUpdateColumn
     * @see #makeDeleteColumn
     */
    protected void makeCUDColumns(String idFieldName) {
        makeCreateUpdateColumn(idFieldName);
        makeDeleteColumn(idFieldName);
    }

    /**
     * Sets the flag to allow creation of new records using this grid, must also have the CREATE permission returned by the service not <code>False</code>. Defaults to <code>false</code>.
     * @param flag a <code>boolean</code> value
     */
    protected void setCanCreateNewRow(boolean flag) {
        canCreateNewRow = flag;
    }

    /**
     * Sets the editable mode for this grid. Defaults to <code>true</code>.
     * To act like a simple list view set this to <code>false</code>.
     * Note: this must be set before the data is loaded.
     * @param flag a <code>boolean</code> value
     */
    public void setEditable(boolean flag) {
        editable = flag;
    }

    /**
     * Sets the data auto loading flag for this grid, if <code>true</code> the data is loaded as soon as the columns are configured, if you need to apply filters set this to <code>false</code>. Defaults to <code>true</code>.
     * Note: obviously this must be set before the data is loaded.
     * @param flag a <code>boolean</code> value
     */
    public void setAutoLoad(boolean flag) {
        autoLoad = flag;
    }

    /**
     * Sets the grid to use a summary row. Defaults to <code>false</code>.
     * Note: this must be set before the grid data is loaded.
     * @param flag a <code>boolean</code> value
     */
    public void setUseSummaryRow(boolean flag) {
        useSummaryRow = flag;
    }

    /**
     * Sets the grid to use a paging toolbar. Defaults to <code>false</code>.
     * Note: this must be set before the grid columns are configured.
     * @param flag a <code>boolean</code> value
     */
    public void setUsePagingToolbar(boolean flag) {
        usePagingToolbar = flag;
    }
    
    /**
     * Creates the pagination toolbar with the excel export button.
     * @param exportToExcel option to create the excel export button or not
     * @throws RequestException 
     */
    private void makePagingToolbar(boolean exportToExcel) throws RequestException {

        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(defaultPageSize);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg(UtilUi.MSG.pagerDisplayMessage());
        pagingToolbar.setEmptyMsg(UtilUi.MSG.pagerDisplayEmpty());
        pagingToolbar.setFirstText(UtilUi.MSG.pagerFirstPage());
        pagingToolbar.setLastText(UtilUi.MSG.pagerLastPage());
        pagingToolbar.setNextText(UtilUi.MSG.pagerNextPage());
        pagingToolbar.setPrevText(UtilUi.MSG.pagerPreviousPage());
        pagingToolbar.setRefreshText(UtilUi.MSG.refresh());
        pagingToolbar.setBeforePageText(UtilUi.MSG.pagerBeforePage());
        pagingToolbar.setAfterPageText(UtilUi.MSG.pagerAfterPage());

        final NumberField pageSizeField = new NumberField();
        pageSizeField.setAllowDecimals(false);
        pageSizeField.setWidth(40);
        pageSizeField.setValue(Integer.valueOf(pagingToolbar.getPageSize()));
        pageSizeField.setSelectOnFocus(true);
        pageSizeField.addListener(new FieldListenerAdapter() {
                @Override public void onSpecialKey(Field field, EventObject e) {
                    if (e.getKey() == EventObject.ENTER) {
                        int pageSize = pageSizeField.getValue().intValue();
                        // do not allow 0 as a page size
                        if (pageSize > 0) {
                            pagingToolbar.setPageSize(pageSize);
                        } else {
                            pageSizeField.setValue(Integer.valueOf(pagingToolbar.getPageSize()));
                        }
                    }
                }
            });

        final ToolTip toolTip = new ToolTip(UtilUi.MSG.pagerEnterPageSize());
        toolTip.applyTo(pageSizeField);
        pagingToolbar.addField(pageSizeField);
        pagingToolbar.addText(UtilUi.MSG.pagerPageSize());
       // UtilUi.debug(""+queryUrl);
        if (exportToExcel) {
        	//check for export permission
        	RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "/crmsfa/control/gwtCheckExportCondition?requester="+queryUrl);
        	Request response = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {	
					String s = response.getText();
					String permission=s.substring(s.indexOf("hasExportPermission")+22, s.indexOf("hasExportPermission")+27);
					if(permission!=null && !permission.trim().equals("") && permission.startsWith("true")){
						pagingToolbar.addSeparator();
			            final ToolbarButton exportToExcelButton = new ToolbarButton(UtilUi.MSG.pagerExportToExcel(), new ButtonListenerAdapter() {
			                    @Override public void onClick(Button button, EventObject e) {
			                        String url = queryUrl + "?" + UtilLookup.PARAM_EXPORT_EXCEL + "=Y";
			                        // pass the filter parameters, the excel spreadsheet content will match the list view content
			                        for (String k : filters.keySet()) {
			                        	//UtilUi.debug("k=>"+k);
			                            url += "&" + k + "=" + filters.get(k);
			                         }
			                        // pass the sorting info
			                        url += "&" + UtilLookup.PARAM_SORT_FIELD + "=" + getStore().getSortState().getField();
			                        url += "&" + UtilLookup.PARAM_SORT_DIRECTION + "=" + getStore().getSortState().getDirection().getDirection();
			                        // pass the column info, since the user can hide and reorder columns, the excel spreadsheet will match the list view configuration
			                        ColumnModel m = getColumnModel();
			                        for (int i = 0; i < m.getColumnCount(); i++) {
			                        	if(!m.isHidden(i)){
			                        		url += "&_" + m.getDataIndex(i) + "_idx=" + i;
			                        	}
			                        }
//			                        UtilUi.debug("URL=>"+url);
			                        UtilUi.redirect(url);
			                    }
			                }, UtilUi.ICON_EXCEL);
			            pagingToolbar.addButton(exportToExcelButton);
						
					}
				}        		
        	});
        }
        setBottomToolbar(pagingToolbar);
    }

    /**
     * Checks if the store has been loaded (loading is asynchronous).
     * @return a <code>boolean</code> value
     */
    public boolean isLoaded() {
        return loaded;
    }

    protected ColumnModel makeColumnModel() {
        ColumnModel model = new ColumnModel(columnConfigs.toArray(new ColumnConfig[columnConfigs.size()]));
        model.setDefaultSortable(true);
        return model;
    }

    protected RecordDef makeRecordDef() {
        // add the definition needed to support summary records
        addFieldDefinition(new StringFieldDef(UtilUi.SUMMARY_ROW_INDICATOR_FIELD));
        // add permissions related record definitions
        addFieldDefinition(new BooleanFieldDef(Permissions.CREATE_FIELD_NAME));
        addFieldDefinition(new BooleanFieldDef(Permissions.UPDATE_FIELD_NAME));
        addFieldDefinition(new BooleanFieldDef(Permissions.DELETE_FIELD_NAME));
        return new RecordDef(fieldDefinitions.toArray(new FieldDef[fieldDefinitions.size()]));
    }

    /** {@inheritDoc} */
    public void notifySuccess() {
        loadFirstPage();
    }

    /**
     * Sets the list view for a lookup.
     */
    public void setLookupMode() {
        for (LinkColumnConfig lookupColumn : lookupColumns) {
            lookupColumn.setLookupMode();
        }
    }

    /**
     * Binds the list view to the given <code>BaseFormPanel</code> so that when a record is selected in the list the form content gets populated by the corresponding data,
     *  and inversely when a field is updated in the form.
     * Note that the form field names and the data field names must match.
     * @param formPanel a <code>BaseFormPanel</code> value
     */
    public void bindToForm(final BaseFormPanel formPanel) {
        if (formPanel == null) {
            return;
        }

        formPanel.setBindedList(this);

        selectionModel.addListener(new RowSelectionListenerAdapter() {
                @Override public void onRowSelect(RowSelectionModel sm, int rowIndex, Record record) {
                    formPanel.loadRecord(record, rowIndex);
                }
            });
    }

    /**
     * Update the record at the given index with the values of the given record.
     * @param index the index in this grid store
     * @param record a <code>Record</code> value with the new values
     */
    public void updateRecord(int index, Record record) {
        if (index < 0 || index > getStore().getCount()) {
            return;
        }
        Record rec = getStore().getAt(index);
        if (rec == null) {
            return;
        }
        // synchronize the fields values, not only get the fields from record which
        // may have less fields than rec
        for (String f : record.getModifiedFields()) {
            rec.set(f, record.getAsObject(f));
        }

    }

    private void regenDefaultValuesArray() {
        if (recordDef != null) {
            List<Object> values = new ArrayList<Object>();
            for (FieldDef fd : recordDef.getFields()) {
                String fn = fd.getName();
                if (defaultValues.containsKey(fn)) {
                    values.add(defaultValues.get(fn));
                } else {
                    // handle default permissions
                    if (Permissions.CREATE_FIELD_NAME.equals(fn)) {
                        values.add(true);
                    } else if (Permissions.UPDATE_FIELD_NAME.equals(fn)) {
                        values.add(true);
                    } else if (Permissions.DELETE_FIELD_NAME.equals(fn)) {
                        values.add(false);
                    } else {
                        values.add(null);
                    }
                }

            }
            defaultValuesArray = values.toArray();
        }
    }

    /**
     * Sets the default value for the given field, used when creating a new row.
     * Defaults to <code>null</code>.
     * @param field the field name, corresponding to its <code>RecordDef</code>
     * @param value an <code>Object</code> value
     */
    protected void setDefaultValue(String field, Object value) {
        defaultValues.put(field, value);
        if (defaultValuesArray != null) {
            regenDefaultValuesArray();
        }
    }

    /**
     * Gets the first summary found in the Store.
     * @return a <code>Record</code> value
     */
    protected Record getSummaryRecord() {
        for (Record rec : store.getRecords()) {
            if (UtilUi.isSummary(rec)) {
                return rec;
            }
        }
        return null;
    }

    /**
     * Inserts a summary row at the end of the list, this can be used by subclasses to display summary information for each columns.
     * @return the created <code>Record</code> object or null if no record could be created
     */
    protected Record addSummaryRow() {
        UtilUi.logDebug("Adding summary row.", MODULE, "addSummaryRow");
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put(UtilUi.SUMMARY_ROW_INDICATOR_FIELD, "Y");
        return addRow(values);
    }

    /**
     * Inserts a new row at the end of the list with the default values IF the create permission flag is set or the grid has the <code>canCreateNewRow</code> flag to true.
     * @return the created <code>Record</code> object or null if no record could be created
     */
    protected Record addRowIfCreatePermission() {
        // check the grid editable flag first, then the canCreateNewRow, then permissions
        if (editable && canCreateNewRow) {
            if (globalPermissions.canCreate()) {
                return addRow();
            }
        }
        return null;
    }

    /**
     * Inserts a new row at the end of the list with the default values IF the create permission flag is set or the grid has the <code>canCreateNewRow</code> flag to true.
     * @param index the index where to insert the row, use negative for an index relative to the end of the list
     * @return the created <code>Record</code> object or null if no record could be created
     */
    protected Record addRowIfCreatePermission(int index) {
        // check the grid editable flag first, then the canCreateNewRow, then permissions
        if (editable && canCreateNewRow) {
            if (globalPermissions.canCreate()) {
                return addRow(index);
            }
        }
        return null;
    }

    /**
     * Inserts a new row at the end of the list with the default values.
     * @return the created <code>Record</code> object or null if no record could be created
     */
    protected Record addRow() {
        return addRow(store.getCount());
    }

    /**
     * Inserts a new row at the given index of the list with the default values.
     * @param index the index where to insert the row, use negative for an index relative to the end of the list
     * @return the created <code>Record</code> object or null if no record could be created
     */
    protected Record addRow(int index) {
        if (defaultValuesArray == null) {
            regenDefaultValuesArray();
        }
        // allow negative index as relative to the end of the list
        if (index < 0) {
            index = store.getCount() + index;
        }
        Record newRecord = recordDef.createRecord(defaultValuesArray);
        store.insert(index, newRecord);
        return newRecord;
    }

    /**
     * Inserts a new row at the given index of the list with given values.
     * @param values a <code>Map</code> of fieldName: value
     * @param index the index where to insert the row, use negative for an index relative to the end of the list
     * @return the created <code>Record</code> object or null if no record could be created
     */
    protected Record addRow(int index, Map<String, Object> values) {
        // allow negative index as relative to the end of the list
        if (index < 0) {
            index = store.getCount() + index;
        }

        if (recordDef != null) {
            List<Object> val = new ArrayList<Object>();
            for (FieldDef fd : recordDef.getFields()) {
                String fn = fd.getName();
                if (values.containsKey(fn)) {
                    val.add(values.get(fn));
                } else {
                    val.add(null);
                }
            }
            Record newRecord = recordDef.createRecord(val.toArray());
            store.insert(index, newRecord);
            return newRecord;
        }
        return null;
    }

    /**
     * Inserts a new row at the end of the list with given values.
     * @param values a <code>Map</code> of fieldName: value
     * @return the created <code>Record</code> object or null if no record could be created
     */
    protected Record addRow(Map<String, Object> values) {
        return addRow(store.getCount(), values);
    }

    /**
     * Handles the save all batch action, this takes all records that need
     * to be created, update or deleted and send them in one request.
     * The posted data is the same format as for a <code>service-multi</code>.
     */
    protected void doBatchAction() {
        String data = makeBatchPostData();
        if (data == null) {
            return;
        }

        RequestBuilder request = new RequestBuilder(RequestBuilder.POST, GWT.getHostPageBaseURL() + saveAllUrl);
        request.setHeader("Content-type", "application/x-www-form-urlencoded");
        request.setRequestData(data);
        request.setCallback(new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    // display error message
                    getEl().unmask();
                    UtilUi.errorMessage(exception.toString());
                }
                public void onResponseReceived(Request request, Response response) {
                    // if it is a correct response, reload the grid
                    getEl().unmask();
                    if (!ServiceErrorReader.showErrorMessageIfAny(response, saveAllUrl)) {
                        // commit store changes
                        getStore().commitChanges();
                        loadFirstPage();
                    }
                }
            });
        try {
            getEl().mask(UtilUi.MSG.loading());
            request.send();
        } catch (RequestException e) {
            // display error message
            UtilUi.errorMessage(e.toString());
        }
    }

    private String makeBatchPostData() {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        index = makeBatchPostData(index, UtilLookup.PARAM_CUD_ACTION_CREATE, getRecordsToCreate(), sb);
        index = makeBatchPostData(index, UtilLookup.PARAM_CUD_ACTION_UPDATE, getRecordsToUpdate(), sb);
        index = makeBatchPostData(index, UtilLookup.PARAM_CUD_ACTION_DELETE, getRecordsToDelete(), sb);
        if (index == 0) {
            return null;
        }
        sb.append("&").append("_rowCount=").append(index);
        return sb.toString();
    }

    private int makeBatchPostData(int index, String action, List<Record> records, StringBuilder sb) {
        for (Record record : records) {
            if (index > 0) {
                sb.append("&");
            }
            // set the submit flag
            sb.append(UtilLookup.ROW_SUBMIT_PREFIX).append(index).append("=").append("Y");
            // add the action, so the service knows what to do with the data
            sb.append("&").append(UtilLookup.PARAM_CUD_ACTION).append(UtilLookup.MULTI_ROW_DELIMITER).append(index).append("=").append(URL.encodeComponent(action));
            for (String field : record.getFields()) {
                // remove client-side permissions
                if (Permissions.isPermissionField(field)) {
                    continue;
                }
                sb.append("&").append(field).append(UtilLookup.MULTI_ROW_DELIMITER).append(index).append("=");
                if (!record.isEmpty(field)) {
                    sb.append(record.getAsString(field));
                }
            }
            // add additional fields that may be required in the service
            if (additionalBatchData != null) {
                for (String extraField : additionalBatchData.keySet()) {
                    sb.append("&").append(extraField).append(UtilLookup.MULTI_ROW_DELIMITER).append(index).append("=").append(additionalBatchData.get(extraField));
                }
            }
            index++;
        }
        return index;
    }

    /**
     * Sets the fields that define a <code>Record</code> primary key.
     * The main use is to check if a <code>Record</code> exists, which implies
     * the primary key fields are all non empty.
     * @param fields the list of fields composing the primary key in a <code>Record</code>
     */
    public void setRecordPrimaryKeyFields(Collection<String> fields) {
        recordPrimaryKeyFields = new HashSet<String>();
        recordPrimaryKeyFields.addAll(fields);
    }

    /**
     * Determines if a given <code>Record</code> exists in the application.
     * For example this is used to determine if a record should be posted to be
     * Created or Updated.
     * @param record a <code>Record</code> value
     * @return a <code>boolean</code> value
     */
    protected boolean recordExists(Record record) {
        for (String f : recordPrimaryKeyFields) {
            if (record.isEmpty(f)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Handles the create or update action on the given row, this uses {@link #recordExists}
     * to determine if the action should be a Create or Update.
     * @param record the <code>Record</code> to update or create
     */
    private void doUpdateCreateAction(Record record) {
        if (recordExists(record)) {
            doUpdateAction(record);
        } else {
            doCreateAction(record);
        }
    }

    /**
     * Handles the update action on the given row.
     * Can override to do some immediate action with the <code>Record</code>.
     * @param record the <code>Record</code> to update or create
     */
    protected void doUpdateAction(Record record) {
    }

    /**
     * Handles the create action on the given row.
     * Can override to do some immediate action with the <code>Record</code>.
     * @param record the <code>Record</code> to update or create
     */
    protected void doCreateAction(Record record) {
    }

    /**
     * Handles the delete action on the given row.
     * Can override to do some immediate action with the <code>Record</code>, else the default implementation
     * is to store the <code>Record</code> if it exists for later batch action and removes it from the grid.
     * @param record the <code>Record</code> to delete
     */
    protected void doDeleteAction(Record record) {
        getStore().remove(record);
        if (recordExists(record)) {
            toDeleteRecords.add(record);
        } else {
            // commit the record else the grid will keep it in its cache
            record.commit();
        }
    }

    /**
     * Gets the list of <code>Record</code> that have been marked for deletion.
     * This can be used if the {@link #doDeleteAction} was not overridden to immediately delete the record
     * to do batch delete instead.
     * @return the <code>List</code> of <code>Record</code> that were marked for deletion
     */
    protected List<Record> getRecordsToDelete() {
        for (Record rec : toDeleteRecords) {
            UtilUi.logInfo("To DELETE: " + UtilUi.toString(rec), MODULE, "getRecordsToDelete");
        }

        return toDeleteRecords;
    }

    /**
     * Gets the list of <code>Record</code> that should be created.
     * This can be used to do batch action.
     * @return the <code>List</code> of <code>Record</code> that should be created
     */
    protected List<Record> getRecordsToCreate() {
        List<Record> toCreate = new ArrayList<Record>();
        for (Record rec : getStore().getModifiedRecords()) {
            if (!recordExists(rec)) {
                UtilUi.logInfo("To CREATE: " + UtilUi.toString(rec), MODULE, "getRecordsToCreate");
                toCreate.add(rec);
            }
        }
        return toCreate;
    }

    /**
     * Gets the list of <code>Record</code> that should be updated.
     * This can be used to do batch action.
     * @return the <code>List</code> of <code>Record</code> that should be updated.
     */
    protected List<Record> getRecordsToUpdate() {
        List<Record> toUpdate = new ArrayList<Record>();
        for (Record rec : getStore().getModifiedRecords()) {
            if (recordExists(rec)) {
                UtilUi.logInfo("To UPDATE: " + UtilUi.toString(rec), MODULE, "getRecordsToUpdate");
                toUpdate.add(rec);
            }
        }
        return toUpdate;
    }

    /**
     * Populates the grid rows extra info, this should return the HTML code to insert as a secondary row for a given record.
     * Default implementation returns <code>null</code>.
     * @param record the row <code>Record</code>
     * @param index the row index
     * @return the HTML to include in the extra row, if <code>null</code> or empty it won't be visible
     */
    protected String getRowBody(Record record, int index) {
        return null;
    }

    /**
     * Registers a <code>LoadableListener</code>.
     * @param listener a <code>LoadableListener</code> value
     */
    public void addLoadableListener(LoadableListener listener) {
        listeners.add(listener);
    }

    protected void notifyLoad() {
        loaded = true;
        for (LoadableListener l : listeners) {
            l.onLoad();
        }
    }

    // those method are from the StoreListener interface

    /** {@inheritDoc} */
    public boolean doBeforeLoad(Store store) {
        return true;
    }

    /** {@inheritDoc} */
    public void onAdd(Store store, Record[] records, int index) {
        UtilUi.logInfo("onAdd, index = " + index, MODULE, "onAdd");
        // we have to trigger a resize so the container can expand with the grid
        syncSize();
    }

    /** {@inheritDoc} */
    public void onClear(Store store) {
    }

    /** {@inheritDoc} */
    public void onDataChanged(Store store) {
        UtilUi.logInfo("onDataChanged", MODULE, "onDataChanged");
    }

    /** {@inheritDoc}
     * The default implementation is to automatically add a new record if the permission is set. */
    public void onLoad(Store store, Record[] records) {
        UtilUi.logInfo("onLoad", MODULE, "onLoad");
        // reset the list of records to delete
        toDeleteRecords = new ArrayList<Record>();
        // find the first record that is always included for permissions
        Record globalPermissionsRecord = records[0];
        globalPermissions = new Permissions(globalPermissionsRecord);
        store.remove(globalPermissionsRecord);

        // if the grid is not editable, or if global permissions do not have create / update or delete, hide those columns
        if (createUpdateIndex > 0 && (!editable || (!globalPermissions.canCreate() && !globalPermissions.canUpdate()))) {
            getColumnModel().setHidden(createUpdateIndex, true);
        }
        if (deleteIndex > 0 && (!editable || !globalPermissions.canDelete())) {
            getColumnModel().setHidden(deleteIndex, true);
        }

        addRowIfCreatePermission();

        if (useSummaryRow) {
            addSummaryRow();
        }

        // now we are all loaded (internal autocompleters had to be loaded for the grid to load)
        notifyLoad();
    }

    /** {@inheritDoc} */
    public void onLoadException(Throwable error) {
    }

    /** {@inheritDoc} */
    public void onRemove(Store store, Record record, int index) {
    }

    /** {@inheritDoc} */
    public void onUpdate(Store store, Record record, Record.Operation operation) {
        UtilUi.logInfo("onUpdate : " + operation.getOperation() + " : " + UtilUi.toString(record), MODULE, "onUpdate");
        // check if we are editing the new record line
        if (operation == Record.EDIT && !recordExists(record)) {
            // if canDelete is already set no need to done anything else
            // else we insert a blank record and set canDelete
            if (!Permissions.canDelete(record)) {
                Permissions.setCanDelete(true, record);
                addRowIfCreatePermission(-1);
            }
        }
    }
}
